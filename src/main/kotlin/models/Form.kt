package com.example.demo.models

import tornadofx.*
import javafx.beans.property.SimpleStringProperty

class Form {
    val customerNameProperty = SimpleStringProperty()
    val peopleRegistrationIdProperty = SimpleStringProperty()
}

class FormModel(private var form: Form): ViewModel() {
    val customerName = bind { form.customerNameProperty }
    val peopleRegistrationId = bind { form.peopleRegistrationIdProperty }
}
