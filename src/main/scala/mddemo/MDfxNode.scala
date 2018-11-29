package mddemo

import com.jpro.webapi.{HTMLView, WebAPI}
import simplefx.core._
import simplefx.all._
import com.sandec.mdfx.MDFXNode
import javafx.scene.{Cursor, Node}
import javafx.scene.control.{ColorPicker, DatePicker}
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class MDfxNode (string: String ) extends MDFXNode(string) { THIS =>

  var defaultNode = new Circle { radius = 100; fill = Color.RED }

  override def generateImage (link: String): Node = {

    val col     = link.substring (0).indexOf(":"); if((col < 0) || (col >= link.length-1)) return defaultNode
    val subject = link.substring (0, col)
    val params  = link.substring (col+1)

    def nodeSpecification(params : String) : Node = {
      params match {
        case "colorpicker" => new ColorPicker
        case "datepicker"  => new DatePicker
        case x             => defaultNode
      }
    }

    def jproSpecification(params : String) : Node = {

      val app = "<jpro-app style=\"width:100%; height:100%;\" href=\"" + params + "\"></jpro-app>"

      println("JPRO-APP: " + app)
      println()

      val htmlview = new HTMLView(app) {
        //minWidthProp = 200
        //minHeightProp = 200
      }

      new StackPane(htmlview)

      //new Circle { radius = 100; fill = Color.BLUE }
    }

    subject match {
      case "node" => nodeSpecification(params)
      case "jpro" => jproSpecification(params)
      case x      => super.generateImage (link)
    }
  }
}
