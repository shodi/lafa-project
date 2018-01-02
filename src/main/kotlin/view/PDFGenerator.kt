package com.example.demo.view

import com.example.demo.models.Order
import com.example.demo.models.OrderList
import org.apache.fop.apps.FopFactory
import org.apache.fop.apps.FOUserAgent
import org.apache.fop.apps.MimeConstants
import java.util.Date
import java.util.Calendar

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.namespace.QName
import javax.xml.bind.Marshaller

import javax.xml.transform.Result
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.sax.SAXResult
import javax.xml.transform.stream.StreamSource

import javafx.collections.ObservableList

import java.io.File
import java.io.*
import java.io.FileWriter
import java.nio.charset.Charset
import org.apache.fop.apps.FOPException

class PDFGenerator (customerName: String, val orderList: ObservableList<Order>) {

    companion object {
        @JvmStatic var RESOURCES_DIR: String = "src//main//resources//"
        @JvmStatic var OUTPUT_DIR: String = "src//main//resources//output//"
    }

    var fileName: String
    

    init {
        var cal = Calendar.getInstance()

        fileName = "${customerName}"
        fileName += "${cal.get(Calendar.DAY_OF_MONTH)}${cal.get(Calendar.MONTH)}${cal.get(Calendar.YEAR)}"
        fileName += "${cal.get(Calendar.HOUR_OF_DAY)}${cal.get(Calendar.MINUTE)}${cal.get(Calendar.SECOND)}"
        fileName += ".xml"
        buildForm()
    }

    private fun buildPDF(xmlFile: File): Unit {
        val xsltFile = File(RESOURCES_DIR + "//template.xls")
        val xmlSource = StreamSource(xmlFile)
        val fopFactory = FopFactory.newInstance()
        val foUserAgent = fopFactory.newFOUserAgent()
        val out = java.io.FileOutputStream(OUTPUT_DIR + "//output.pdf")

        val fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out)

        val factory = TransformerFactory.newInstance()
        val transformer = factory.newTransformer(StreamSource(xsltFile))
        val res = SAXResult(fop.getDefaultHandler())
        transformer.setParameter("versionParam", "1.0")
        transformer.transform(xmlSource, res)
        out.close()
    }

    private fun buildForm(): Unit {
        val file = File(RESOURCES_DIR + fileName)
        try {
            val contextObj: JAXBContext = JAXBContext.newInstance(Order::class.java)
//            val root: JAXBElement<Array<Order>> = JAXBElement<Array<Order>>(
//                    QName("orders"),
//                    Array<Order>::class.java,
//                    orderList.getItems()
//            )
            val marshallerObj: Marshaller = contextObj.createMarshaller()
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            val stringWriter = StringWriter()
            stringWriter.use {
                marshallerObj.marshal(orderList, stringWriter)
            }
            println(stringWriter.toString())
//            file.writeText(stringWriter.toString())
            file.writeText("""<?xml version="1.0" encoding="UTF-8"?>
            <?xml-stylesheet type="application/xml"?>
            <users-data>
                <header-section>
                    <data-type id="019">User Bill Data</data-type>
                    <process-date>Thursday December 9 2016 00:04:29</process-date>
                </header-section>
                <user-bill-data>
                    <full-name>John Doe</full-name>
                    <postal-code>34239</postal-code>
                    <national-id>123AD329248</national-id>
                    <price>17.84</price>
                </user-bill-data>
                <user-bill-data>
                    <full-name>Michael Doe</full-name>
                    <postal-code>54823</postal-code>
                    <national-id>942KFDSCW322</national-id>
                    <price>34.50</price>
                </user-bill-data>
                <user-bill-data>
                    <full-name>Jane Brown</full-name>
                    <postal-code>66742</postal-code>
                    <national-id>ABDD324KKD8</national-id>
                    <price>69.36</price>
                </user-bill-data>
            </users-data>
            """)
            try {
//                buildPDF(file)
            } catch (e: FOPException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: TransformerException) {
                e.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

