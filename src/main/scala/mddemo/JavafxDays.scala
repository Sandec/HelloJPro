package mddemo

import java.nio.charset.Charset
import simplefx.core._
import simplefx.all._

object JavafxDays extends App
@SimpleFXApp class JavafxDays {

  println("HELLO")

  val defaultdoc = org.apache.commons.io.IOUtils.toString(
    getClass.getResource("/jpro/doc/md/default.md"),Charset.forName("UTF-8"))

  println("----------------")
  println(defaultdoc)
  println("----------------")
  val md = new VBox(new MDfxNode (defaultdoc)) {
    padding = Insets(50,50,50,50)

    override def layoutChildren(): Unit = {
      println("## LAYOUT CHILDREN")
      if ((this.scene ne null) && WebAPI.isBrowser) {
        WebAPI.getWebAPI(this.scene).requestLayout(this.scene)
        super.layoutChildren()
      } else {
        super.layoutChildren()
      }
    }
  }

  scene = new Scene(md, 600, 5000, Color.LIGHTBLUE)

}

