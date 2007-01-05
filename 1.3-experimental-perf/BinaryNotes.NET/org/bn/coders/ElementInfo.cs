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
using System.Reflection;
using org.bn.attributes;

namespace org.bn.coders
{	
	public class ElementInfo
	{
        private ASN1Element element;
        private ICustomAttributeProvider parentAnnotatedClass;

        public ICustomAttributeProvider ParentAnnotatedClass
        {
            get { return parentAnnotatedClass; }
            set { parentAnnotatedClass = value; }
        }

        public ASN1Element Element
        {
            get { return element; }
            set { element = value; }
        }
        private ICustomAttributeProvider annotatedClass;

        public ICustomAttributeProvider AnnotatedClass
        {
            get { return annotatedClass; }
            set { annotatedClass = value; }
        }

        public ElementInfo()
        {
        }

        public ElementInfo(ICustomAttributeProvider annotatedClass)
        {
            AnnotatedClass = annotatedClass;
        }

        public ElementInfo(ICustomAttributeProvider annotatedClass, ASN1Element element)
        {
            AnnotatedClass = annotatedClass;
            Element = element;
        }

        public bool isAttributePresent<T>() 
        {
            return CoderUtils.isAttributePresent<T>(annotatedClass);
        }

        public T getAttribute<T>()
        {
            return CoderUtils.getAttribute<T>(annotatedClass);
        }

        public bool isParentAttributePresent<T>()
        {
            return CoderUtils.isAttributePresent<T>(parentAnnotatedClass);
        }

        public T getParentAttribute<T>()
        {
            return CoderUtils.getAttribute<T>(parentAnnotatedClass);
        }

	}
}