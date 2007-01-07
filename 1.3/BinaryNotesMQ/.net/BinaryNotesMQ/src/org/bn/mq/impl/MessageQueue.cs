/*
* Copyright 2006 Abdulla G. Abdurakhmanov (abdulla.abdurakhmanov@gmail.com).
* 
* Licensed under the LGPL, Version 2 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*      http://www.gnu.org/copyleft/lgpl.html
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
* With any your questions welcome to my e-mail 
* or blog at http://abdulla-a.blogspot.com.
*/
using System;
using System.Threading;
using System.Collections.Generic;
using org.bn.mq.protocol;
using org.bn.mq.net;

namespace org.bn.mq.impl
{
	
	public class MessageQueue<T> : IMessageQueue<T>, ITransportConnectionListener, ITransportReader
	{
        private IQueue<T> queue = new Queue<T>();
		virtual public IQueue<T> Queue
		{
			get
			{
				return queue;
			}
			
			set
			{
				lock (queue)
				{
					this.queue = value;
				}
			}			
		}
        private static long messageIdGenerator = 1;

        protected internal IPersistenceQueueStorage<T> persistStorage;
		virtual public IPersistenceQueueStorage<T> PersistenceStorage
		{
			set
			{
				this.persistStorage = value;
			}

            get
            {
                return this.persistStorage;
            }
			
		}

        virtual public System.String QueuePath
		{
			get
			{
				return queuePath;
			}
			
		}
		private ITransport transport;
		private System.String queuePath;
		
		private int callCurId = 0;
		private Thread senderThread;

		private bool stopThreadFlag = false;
		protected internal AutoResetEvent awaitMessageEvent = new AutoResetEvent(false);
		protected internal IDictionary<String, IConsumer<T> > consumers = new Dictionary<String, IConsumer<T>>();
		
		public MessageQueue(string queuePath, ITransport transport)
		{
            senderThread = new Thread(new ThreadStart(this.Run));
            this.transport = transport;
			this.queuePath = queuePath;
			this.transport.addConnectionListener(this);
			this.transport.addReader(this);
			senderThread.Name = "BNMessageQueue-" + queuePath;
			start();
		}
		
		public virtual void  sendMessage(IMessage<T> message)
		{
			if (message.Body == null)
				throw new Exception("Incorrect empty message body is specified to send!");
			lock (queue)
			{
				if (message.Mandatory)
					persistStorage.registerPersistenceMessage(message);
				queue.push(message);
			}
			awaitMessageEvent.Set();
		}
		
		public virtual T call(T args, string consumerId, int timeout)
		{
			Message<T> envelope = new Message<T>();
			envelope.Id = (queuePath + "/call-" + callCurId++);
			envelope.Body = (args);
			MessageEnvelope argsEnv = envelope.createEnvelope();
			MessageEnvelope result = null;
			argsEnv.Body.MessageUserBody.QueuePath = this.QueuePath;
			argsEnv.Body.MessageUserBody.ConsumerId = consumerId;
			IConsumer<T> consumer = null;
			lock (consumers)
			{
				if (!consumers.ContainsKey(consumerId))
				{
					throw new Exception("Consumer with id:" + consumerId + " not found!");
				}
				else
				{
					consumer = consumers[consumerId];
				}
			}
			
            if (consumer is IRemoteConsumer<T>)
			{
				ITransport consTransport = ((IRemoteConsumer<T>)consumer).NetworkTransport;
				result = consTransport.call(argsEnv, timeout);
				envelope.fillFromEnvelope(result);
				return envelope.Body;
			}
			else
				throw new System.Exception("Call enabled only for remote consumer !");
		}
		
		public virtual T call(T args, string consumerId)
		{
			return call(args, consumerId, 120);
		}

        public virtual void callAsync(T args, string consumerId, ICallAsyncListener<T> listener, int timeout)
		{
            Message<T> envelope = new Message<T>();
			envelope.Id = (queuePath + "/call-" + callCurId++);
			envelope.Body = (args);
			MessageEnvelope argsEnv = envelope.createEnvelope();
			MessageEnvelope result = null;
			argsEnv.Body.MessageUserBody.QueuePath = this.QueuePath;
			argsEnv.Body.MessageUserBody.ConsumerId = consumerId;
			IConsumer<T> consumer = null;
			lock (consumers)
			{
				if (!consumers.ContainsKey(consumerId))
				{
					throw new Exception("Consumer with id:" + consumerId + " not found!");
				}
				else
				{
					consumer = consumers[consumerId];
				}
			}
            if (consumer is IRemoteConsumer<T>)
			{
				ITransport consTransport = ((IRemoteConsumer<T>)consumer).NetworkTransport;
				consTransport.callAsync(argsEnv, new ProxyCallAsyncListener<T>(this, listener), timeout);
			}
			else
				throw new System.Exception("Call enabled only for remote consumer !");
		}

        public virtual void callAsync(T args, string consumerId, ICallAsyncListener<T> listener)
		{
			callAsync(args, consumerId, listener, 120);
		}
		
		public virtual void  addConsumer(IConsumer<T> consumer)
		{
			addConsumer(consumer, false);
		}
		
		public virtual void  addConsumer(IConsumer<T> consumer, bool persistence)
		{
			addConsumer(consumer, persistence, null);
		}
		
		public virtual void  addConsumer(IConsumer<T> consumer, bool persistence, string filter)
		{
			lock (consumers)
			{
				if (consumers.ContainsKey(consumer.Id))
				{
					throw new Exception("Consumer with id:" + consumer.Id + " is already subscription!");
				}
				else
				{
					consumers[consumer.Id] = consumer;
					if (persistence)
						persistStorage.persistenceSubscribe(consumer);
				}
			}
		}
		
		public virtual void  delConsumer(IConsumer<T> consumer)
		{
			lock (consumers)
			{
                persistStorage.persistenceUnsubscribe(consumer);
				if (!consumers.ContainsKey(consumer.Id))
				{
					throw new Exception("Consumer with id:" + consumer.Id + " doesn't have any subscription!");
				}
				else
				{
					consumers.Remove(consumer.Id);
				}
			}
		}
		
		public virtual void  Run()
		{
			IMessage<T> message = null;
			do 
			{
				lock (queue)
				{
					message = queue.getNext();
				}
				if (message == null)
				{					
					awaitMessageEvent.WaitOne();
				}
                else
				{
					lock (consumers)
					{
						foreach(IConsumer<T> entry in consumers.Values)
						{
							entry.onMessage(message);
							if (message.Mandatory)
							{
								try
								{
									persistStorage.removeDeliveredMessage(entry, message);
								}
								catch (Exception e)
								{
									Console.WriteLine(e);
								}
							}
						}
					}
				}
			}
			while (!stopThreadFlag);
		}
		
		public virtual void  stop()
		{
			lock (this)
			{
				stopThreadFlag = true;
				try
				{
					if (senderThread.IsAlive)
					{
						awaitMessageEvent.Set();
						this.transport.delConnectionListener(this);
						this.transport.delReader(this);
						senderThread.Join();
					}
				}
				catch (ThreadInterruptedException e)
				{
					Console.WriteLine(e);
				}
			}
		}
		
		public virtual void  start()
		{
			lock (this)
			{
				stopThreadFlag = false;
				if (!senderThread.IsAlive)
				{
					senderThread.Start();
				}
			}
		}

        ~MessageQueue() 
        {
            stop();
        }
		
		private void  onReceiveSubscribeRequest(MessageEnvelope message, ITransport transport)
		{
			RemoteConsumer<T> remoteConsumer = new RemoteConsumer<T>(
                message.Body.SubscribeRequest.ConsumerId, transport
            );
			
			MessageEnvelope resultMsg = new MessageEnvelope();
			MessageBody body = new MessageBody();
			SubscribeResult subscribeResult = new SubscribeResult();
			SubscribeResultCode subscribeResultCode = new SubscribeResultCode();
			body.selectSubscribeResult(subscribeResult);
			resultMsg.Body = body;
			resultMsg.Id = message.Id;
			try
			{
				addConsumer(remoteConsumer, message.Body.SubscribeRequest.Persistence, message.Body.SubscribeRequest.Filter);
				subscribeResultCode.Value = SubscribeResultCode.EnumType.success;

				if (message.Body.SubscribeRequest.Persistence)
				{
					IList<IMessage<T>> messages = persistStorage.getMessagesToSend(remoteConsumer);
					lock (queue)
					{
						queue.push(messages);
					}
					awaitMessageEvent.Set();
				}
			}
			catch (System.Exception e)
			{
				subscribeResultCode.Value = SubscribeResultCode.EnumType.alreadySubscription;
				subscribeResult.Details = (e.ToString());
			}
			subscribeResult.Code = subscribeResultCode;
			try
			{
				transport.sendAsync(resultMsg);
			}
			catch (System.Exception e)
			{
				Console.WriteLine(e);
			}
		}
		
		private void  onReceiveUnsubscribeRequest(MessageEnvelope message, ITransport transport)
		{
			IConsumer<T> consumer = null;
			lock (consumers)
			{
                if(consumers.ContainsKey(message.Body.UnsubscribeRequest.ConsumerId))
				    consumer = consumers[message.Body.UnsubscribeRequest.ConsumerId];
			}

			MessageEnvelope resultMsg = new MessageEnvelope();
			MessageBody body = new MessageBody();
			UnsubscribeResult unsubscribeResult = new UnsubscribeResult();
			UnsubscribeResultCode unsubscribeResultCode = new UnsubscribeResultCode();
			body.selectUnsubscribeResult(unsubscribeResult);
			resultMsg.Body = body;
			resultMsg.Id = message.Id;
			
			if (consumer != null)
			{
				try
				{
					delConsumer(consumer);
				}
				catch (System.Exception e)
				{
					// TODO
				}
				unsubscribeResultCode.Value = UnsubscribeResultCode.EnumType.success;
			}
			else
			{
				unsubscribeResultCode.Value = UnsubscribeResultCode.EnumType.subscriptionNotExists;
			}
			unsubscribeResult.Code = unsubscribeResultCode;
			try
			{
				transport.sendAsync(resultMsg);
			}
			catch (System.Exception e)
			{
				Console.WriteLine(e);
			}
		}
		
		public virtual bool onReceive(MessageEnvelope message, ITransport transport)
		{
			if (message.Body.isSubscribeRequestSelected() && message.Body.SubscribeRequest.QueuePath.ToUpper().Equals(queuePath.ToUpper()))
			{
				onReceiveSubscribeRequest(message, transport);
				return true;
			}
            else
			if (message.Body.isUnsubscribeRequestSelected() && message.Body.UnsubscribeRequest.QueuePath.ToUpper().Equals(queuePath.ToUpper()))
			{
				onReceiveUnsubscribeRequest(message, transport);
				return true;
			}
			return false;
		}
		
		public virtual void  onConnected(ITransport transport)
		{
		}
		
		public virtual void  onDisconnected(ITransport transport)
		{
			lock (consumers)
			{
                IDictionary<String, IConsumer<T> > newConsumers = new Dictionary<String, IConsumer<T>>();
				foreach(IConsumer<T> entry in consumers.Values)
				{
					if (entry is IRemoteConsumer<T>)
					{
						IRemoteConsumer<T> consumer = (IRemoteConsumer<T>)entry;
						if (!consumer.NetworkTransport.Equals(transport))
						{
							newConsumers[entry.Id] = entry;
						}
					}
				}
				consumers.Clear();
				putAll(newConsumers, consumers);
			}
		}

        private void putAll(IDictionary<string, IConsumer<T>> source, IDictionary<string, IConsumer<T>> target)
        {
            IEnumerator<KeyValuePair<string, IConsumer<T>>> enumerator = source.GetEnumerator();
            while (enumerator.MoveNext())
            {
                KeyValuePair<string, IConsumer<T>> keyPair = enumerator.Current;
                target.Add(keyPair.Key, keyPair.Value);
            }
        }
		
		public virtual IMessage<T> createMessage(T body)
		{
			Message<T> result = new Message<T>();
			result.Body = body;
			result.Id = this.QueuePath+"/Message "+messageIdGenerator++;
			result.QueuePath = this.QueuePath;
			return result;
		}
		
		public virtual IMessage<T> createMessage()
		{
			return createMessage(default(T));
		}
	}
}
