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

  val defaultNode : Node = new Circle { radius = 100; fill = Color.RED }

  override def generateImage (link: String): Node = {

    @Bind var displayNode : Node = new Circle { radius = 100; fill = Color.RED }    // The Node currently displayed.

    val col     = link.indexOf(":"); if((col < 0) || (col >= link.length-1)) return defaultNode
    val subject = link.substring (0, col)
    val params  = link.substring (col+1)

    def nodeSpecification(params : String) : Node = {
      params match {
        case "colorpicker" => new ColorPicker
        case "datepicker"  => new DatePicker
        case _             => defaultNode
      }
    }

    def jproSpecification(params : String) : Node = {
      val app = "<jpro-app style=\"width:100%; height:100%;\" href=\"" + params + "\"></jpro-app>"
      println("JPRO-APP: " + app)
      println()
      val htmlview = new HTMLView(app) //{ minWidthProp = 300; minHeightProp = 300 }
      new StackPane(htmlview)
    }

    def imageSpecification(params : String) : Node = {
      println("IMAGE: " + params)
      println()
      val semi = params.indexOf(";")
      if(semi < 1) return super.generateImage (params)
      else {
        val firstParams     = params.substring (0, semi); println("FIRSTPARAMS: " + firstParams)
        val secondCol       = semi+1 + params.substring (semi+1).indexOf(":"); if((secondCol < 0) || (secondCol >= params.length-1)) return defaultNode
        val secondSubject   = params.substring (semi+1, secondCol)
        val secondParams    = params.substring (secondCol+1)
        var secondNode:Node = defaultNode

        println("SECONDSUBJECT: " + secondSubject)
        println("SECONDPARAMS: "  + secondParams)

        secondSubject match {
          case "node"  => { secondNode = nodeSpecification   (secondParams) }
          case "jpro"  => { secondNode = jproSpecification   (secondParams) }
          case "image" => { secondNode = imageSpecification  (secondParams) }
          case _       => { println("Second Subject is wrong: " + secondSubject); secondNode = super.generateImage (link        ) }
        }
        val firstNode = super.generateImage (firstParams)
        firstNode.onMouseClicked --> { x => println("Was clicked ... ***************************") }
        //secondNode.visible = false; in(0.3 s) --> {secondNode.visible = true}
        return new StackPane(secondNode, firstNode)
      }
    }

    subject match {
      case "node"  => { displayNode := nodeSpecification   (params) }
      case "jpro"  => { displayNode := jproSpecification   (params) }
      case "image" => { displayNode := imageSpecification  (params) }
      case _       => { displayNode := super.generateImage (link  ) }
    }
    displayNode
  }
}
