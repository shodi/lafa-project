package com.example.demo.view 

import tornadofx.*
import io.reactivex.rxkotlin.*
import javafx.beans.property.SimpleStringProperty


import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAccessType

@XmlRootElement
class FormObject(@XmlAttribute val customer: String) {
    constructor() : this("no value")
}
