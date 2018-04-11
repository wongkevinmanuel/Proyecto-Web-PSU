package com.uteq.psu.controler.util;

import com.itextpdf.text.*;
import static com.itextpdf.text.Annotation.URL;
import static com.itextpdf.text.pdf.PdfName.URL;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import com.itextpdf.text.pdf.PdfPTable;
import java.net.URL;

public class Pagina {

    public Pagina() {

    }

    public boolean Crear_pdf(String ubicacionArchivo,String logo_actual,String titulo_solicitud_actual, String fecha_solicitud_actual, String oficio_inicio_actual, String texto_oficio_actual, String motivo_adjunto_actual, String[] motivos, String documento_adjunto_actual, String[] documentos, String fin_oficio_actual, String firma_estudiante_actual) {

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //+                             Creacion del pdf                                 +
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Margen del documennto
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++       
        // Datos de los motivos y documentos
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String cargar_motivos = "";
        String cargar_documentos = "";

        for (int i = 0; i < motivos.length; i++) {
            cargar_motivos += "     * " + motivos[i].toString() + "\n";
        }

        for (int i = 0; i < documentos.length; i++) {
            cargar_documentos += "     * " + documentos[i].toString() + "\n";
        }

        boolean generadoPDF = false;
        float margen_izquierdo = 70;
        float margen_derecho = 70;
        float margen_superior = 20;
        float margen_inferior = 2;
        float interlineado = 17;

        try {
            // Creación del documento
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            Document documento = new Document(PageSize.A4, margen_izquierdo, margen_derecho, margen_superior, margen_inferior);
            //PdfWriter.getInstance(documento, new FileOutputStream("solicitud.pdfubicacionArchivo"));
            PdfWriter.getInstance(documento, new FileOutputStream(ubicacionArchivo));
            documento.open();

            // Tabla principal para el oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            PdfPTable oficio = new PdfPTable(1);

            // Celdas para el cuerpo del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            PdfPCell encabezado = new PdfPCell();
            PdfPCell cuerpo = new PdfPCell();
            PdfPCell pie = new PdfPCell();

            // Definición del ancho del documento
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            oficio.setWidthPercentage(100);
            encabezado.setBorder(Rectangle.NO_BORDER);
            cuerpo.setBorder(Rectangle.NO_BORDER);
            pie.setBorder(Rectangle.NO_BORDER);

            // Logo del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++           
            Image logo = Image.getInstance(logo_actual);

            // Encabezado del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //encabezado.addElement(p);
            encabezado.addElement(logo);
            encabezado.setFixedHeight(60f);
            oficio.addCell(encabezado);

            // Detalles del cuerpo del oficio
            //Titulo
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            cuerpo.setFixedHeight(710f);
            Paragraph parrafo;
            parrafo = new Paragraph(interlineado, titulo_solicitud_actual.toUpperCase(), FontFactory.getFont("ARIAL", 11, Font.BOLD, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_CENTER);
            cuerpo.addElement(parrafo);

            // Fecha del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //SimpleDateFormat formateador = new SimpleDateFormat(fecha_solicitud_actual);
            //Date fechaDate = new Date();
            //String fecha_solicitud = formateador.format(fechaDate);

            //parrafo = new Paragraph(interlineado, fecha_solicitud, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            parrafo = new Paragraph(interlineado, fecha_solicitud_actual, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            cuerpo.addElement(parrafo);

            // Inicio del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            parrafo = new Paragraph(interlineado, oficio_inicio_actual, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.addElement(parrafo);

            // Texto del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            parrafo = new Paragraph(interlineado, texto_oficio_actual, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.addElement(parrafo);

            // Motivos del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            parrafo = new Paragraph(interlineado, motivo_adjunto_actual, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.addElement(parrafo);

            parrafo = new Paragraph(interlineado, cargar_motivos, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.addElement(parrafo);

            // Tipos de documentos adjuntos
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            parrafo = new Paragraph(interlineado, documento_adjunto_actual, FontFactory.getFont("ARIAL", 11, Font.BOLD, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.addElement(parrafo);

            //parrafo = new Paragraph(interlineado, cargar_documentos, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            //parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            //cuerpo.addElement(parrafo);

            // Final de oficio, agradecimiento
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            parrafo = new Paragraph(interlineado, fin_oficio_actual, FontFactory.getFont("ARIAL", 11, Font.NORMAL, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.addElement(parrafo);

            // Firma
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            parrafo = new Paragraph(10, firma_estudiante_actual, FontFactory.getFont("ARIAL", 10, Font.BOLD, BaseColor.BLACK));
            parrafo.setAlignment(Element.ALIGN_CENTER);
            cuerpo.addElement(parrafo);
            oficio.addCell(cuerpo);

            // Pie del oficio
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            String texto_pie_actual = "Campus ''Ingeniero Manuel Agustín Haz Álvarez''"
                    + "\nAv. Quito km 1 1/2 vía Santo Domingo de los Tsáchilas"
                    + "\nTelf. + (593 5) 2750320 / 2751430 Ext. 8001"
                    + "\nwww.uteq.edu.ec";

            Paragraph ubicacion;
            ubicacion = new Paragraph(6, texto_pie_actual, FontFactory.getFont("ARIAL", 6, Font.NORMAL, BaseColor.BLACK));
            ubicacion.setAlignment(Element.ALIGN_RIGHT);
            pie.setFixedHeight(30f);
            pie.addElement(ubicacion);
            oficio.addCell(pie);

            // Fin del oficio, se carga el documento y se cierra
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            documento.add(oficio);
            documento.close();
            generadoPDF = true;
        } catch (Exception de) {
            de.printStackTrace();
            generadoPDF = false;
        }
        return generadoPDF;
    }
}
