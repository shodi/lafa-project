package com.example.demo.models

import tornadofx.*

import javafx.beans.property.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlType

class CustomerForm {
    val customerNameProperty = SimpleStringProperty()
    val peopleRegistrationIdProperty = SimpleStringProperty()
    val ufProperty = SimpleStringProperty()
    val cityProperty = SimpleStringProperty()
    val customerAddressProperty = SimpleStringProperty()
    val billingAddressProperty = SimpleStringProperty()
    val cepProperty = SimpleStringProperty()
    val billingCepProperty = SimpleStringProperty()
    val ccmProperty = SimpleStringProperty()
    val inscEstProperty = SimpleStringProperty()
}

class CustomerModel(private var customer: CustomerForm): ViewModel() {
    val customerName = bind { customer.customerNameProperty }
    val peopleRegistrationId = bind { customer.peopleRegistrationIdProperty }
    val uf = bind { customer.ufProperty }
    val city = bind { customer.cityProperty }
    val customerAddress = bind { customer.customerAddressProperty }
    val billingAddress = bind { customer.billingAddressProperty }
    val cep = bind { customer.cepProperty }
    val billingCep = bind { customer.billingCepProperty }
    val ccm = bind { customer.ccmProperty }
    val inscEst = bind { customer.inscEstProperty }
}