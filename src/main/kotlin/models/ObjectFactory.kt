package com.example.demo.models

import javax.xml.bind.JAXBElement
import javax.xml.bind.annotation.XmlElementDecl
import javax.xml.bind.annotation.XmlRegistry
import javax.xml.namespace.QName

@XmlRegistry
class ObjectFactory {
    private val _Receipt_QNAME: QName = QName("", "receipt")

    fun createCustomer(): Customer {
        return Customer()
    }

    fun createOrder(): Order {
        return Order()
    }

    @XmlElementDecl(namespace = "", name = "receipt")
    fun createReceipt(value: Receipt): JAXBElement<Receipt> {
        return JAXBElement<Receipt>(_Receipt_QNAME, Receipt::class.java, null, value)
    }
}

