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

package org.bn.mq.net.tcp;

import java.util.LinkedList;

public class ConnectorStorage {
    private LinkedList<ConnectorTransport> awaitingConnect = new LinkedList<ConnectorTransport> ();
    
    public ConnectorStorage() {
    }
    
    public void addAwaitingTransport(ConnectorTransport transport) {
        synchronized (awaitingConnect) {
            awaitingConnect.add(transport);
        }
    }
    
    public void removeAwaitingTransport(ConnectorTransport transport) {
        synchronized (awaitingConnect) {
            awaitingConnect.remove(transport);
        }
    }    
    
    public ConnectorTransport getAwaitingTransport() {
        ConnectorTransport result = null;
        synchronized (awaitingConnect) {
            if(!awaitingConnect.isEmpty()) {
                result = awaitingConnect.getFirst();
                awaitingConnect.removeFirst();
            }
        }
        return result;
    }

    public void clear() {
        synchronized (awaitingConnect) {
            awaitingConnect.clear();
        }
    }
    
    public void finalize() {
        clear();
    }
}
