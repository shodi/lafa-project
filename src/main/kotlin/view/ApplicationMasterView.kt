package view

import tornadofx.View
import javafx.scene.layout.VBox

class ApplicationMasterView: View() {
    override val root: VBox by fxml("/ApplicationMasterView.fxml")
}