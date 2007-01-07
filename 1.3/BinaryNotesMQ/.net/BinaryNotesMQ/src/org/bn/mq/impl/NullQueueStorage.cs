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

    public class NullQueueStorage<T> : IPersistenceQueueStorage<T>
	{
        private IList<IMessage<T>> nullMessages = new List<IMessage<T>>();

        public IList<IMessage<T>> getMessagesToSend(IConsumer<T> consumer)
        {
            return nullMessages;
        }

        public void persistenceSubscribe(IConsumer<T> consumer)
        {
        }

        public void persistenceUnsubscribe(IConsumer<T> consumer)
        {
        }

        public void registerPersistenceMessage(IMessage<T> message)
        {
        }

        public void removeDeliveredMessage(IConsumer<T> consumer, IMessage<T> message)
        {
        }

        public void close()
        {
        }
    }
}