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

package org.bn.mq.impl;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;

import org.bn.mq.IMQConnection;
import org.bn.mq.IRemoteSupplier;
import org.bn.mq.ISupplier;
import org.bn.mq.net.ITransport;
import org.bn.mq.protocol.LookupRequest;
import org.bn.mq.protocol.LookupResultCode;
import org.bn.mq.protocol.MessageBody;
import org.bn.mq.protocol.MessageEnvelope;

public class MQConnection implements IMQConnection {
    protected ITransport transport;
    protected final int callTimeout = 30;
    protected Map<String,ISupplier> suppliers = new HashMap<String,ISupplier>();
    
    public MQConnection(ITransport transport) {
        this.transport = transport;
    }

    public IRemoteSupplier lookup(String supplierName) throws Exception {
        LookupRequest request = new LookupRequest();
        request.setSupplierName(supplierName);
        MessageEnvelope message = new MessageEnvelope();
        MessageBody body = new MessageBody();
        body.selectLookupRequest(request);
        message.setBody(body);
        message.setId(this.toString());
        MessageEnvelope result = transport.call(message,callTimeout);
        if (result.getBody().getLookupResult().getCode().getValue() == LookupResultCode.EnumType.success ) {
            return new RemoteSupplier(this);
        }
        else
            throw new Exception("Error when accessing to supplier '"+supplierName+"': "+ result.getBody().getLookupResult().getCode().getValue().toString());
    }

    public void registerSupplier(ISupplier supplier) {
        synchronized(suppliers) {
            suppliers.put(supplier.getId(),supplier);
        }
    }

    public void unregisterSupplier(ISupplier supplier) {
        synchronized(suppliers) {
            suppliers.remove(supplier.getId());
        }
    }

    public URI getAddr() {
        return transport.getAddr();
    }

    public void close() {
        transport.close();
    }
}
