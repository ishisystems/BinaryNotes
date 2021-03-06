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
using System.Collections.Generic;
using org.bn.mq.protocol;
using org.bn.mq.net;

namespace org.bn.mq.impl
{
	
	public class InMemoryQueueStorage<T> : IPersistenceQueueStorage<T>
	{
		private string queueStorageName;
		private IDictionary<string, IList<IMessage<T>>> storage = new Dictionary<string, IList<IMessage<T>>>();

        private IList<IMessage<T>> nullList = new List<IMessage<T>>();
		
		public InMemoryQueueStorage(string queueStorageName)
		{
			this.queueStorageName = queueStorageName;
		}

        public virtual IList<IMessage<T>> getMessagesToSend(IConsumer<T> consumer)
		{
            IList<IMessage<T>> result = nullList;
			lock (storage)
			{
                if (storage.ContainsKey(consumer.Id))
                {
                    result = storage[consumer.Id];
                }
			}
			return result;
		}

        public virtual void removeDeliveredMessage(IConsumer<T> consumer, IMessage<T> message)
		{
			lock (storage)
			{
                if (storage.ContainsKey(consumer.Id))
                {
				    IList<IMessage<T>> messages = storage[consumer.Id];
					foreach(IMessage<T> item in messages)
					{
						if (item.Id.Equals(message.Id))
						{
							messages.Remove(item);
							break;
						}
					}
				}
			}
		}

        public virtual void persistenceSubscribe(IConsumer<T> consumer)
		{
			lock (storage)
			{
				if (!storage.ContainsKey(consumer.Id))
				{
                    IList<IMessage<T>> messages = new List<IMessage<T>>();
                    storage[consumer.Id] = messages;
				}
			}
		}

        public virtual void persistenceUnsubscribe(IConsumer<T> consumer)
		{
			lock (storage)
			{
				storage.Remove(consumer.Id);
			}
		}

        public virtual void registerPersistenceMessage(IMessage<T> message)
		{
			lock (storage)
			{
				foreach(IList<IMessage<T>>item in storage.Values)
				{
					item.Add(message);
				}
			}
		}
		
		public virtual void  close()
		{
		}
	}
}