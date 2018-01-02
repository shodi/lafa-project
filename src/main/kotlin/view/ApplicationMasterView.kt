package com.example.demo.view

import tornadofx.*
import javafx.scene.layout.GridPane
import javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE

class ApplicationMasterView : View() {
    override val root = GridPane()

    val mainView: MasterView by inject()

    init {
        with(root) {
            tabpane {
                tabClosingPolicy = UNAVAILABLE
                tab("shodi") {
                    this += mainView
                }
                tab("teste") {

                }
            }
        }
    }
}
