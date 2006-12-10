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

import java.util.Date;

import org.bn.mq.IMessage;
import org.bn.mq.IRemoteSupplier;

public class Message<T> implements IMessage<T> {
    public String getId() {
        return null;
    }

    public IRemoteSupplier getSupplier() {
        return null;
    }

    public int getPriority() {
        return 0;
    }

    public void setPrioriy(int prio) {
    }

    public boolean isMandatory() {
        return false;
    }

    public void setMandatory(boolean flag) {
    }

    public Date getLifeTime() {
        return null;
    }

    public void setLifeTime(Date lifeTime) {
    }

    public T getBody() {
        return null;
    }

    public void setBody(T body) {
    }
}
