package com.shield.eaarogya.Service.ServiceImpl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.shield.eaarogya.DTO.PrescriptionDetails;
import com.shield.eaarogya.Entity.Patient;
import com.shield.eaarogya.Service.PatientService;
import com.shield.eaarogya.Service.PdfService;
import com.shield.eaarogya.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PatientService patientService;

    @Override
    public ByteArrayInputStream generatePdf(int prescriptionId) {

        // Fetching the prescription which has to be printed
        try {
            PrescriptionDetails prescription = prescriptionService.getPrescriptionById(prescriptionId);

            // Fetching Patient Details using Patient Id.
            Patient patient = patientService.getPatientByPatientId(prescription.getPatientId());

            // Create a ByteArrayOutputStream object
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Document object of lowgie class
            Document document = new Document();

            // now whatever we'll write in document should be reflected in out attribute, so we'll use the following method.
            PdfWriter pdfWriter = PdfWriter.getInstance(document, out);

            // Open the document and start writing
            document.open();

            // Adding background color
            // Set the background color of the document
            PdfContentByte canvas = pdfWriter.getDirectContentUnder();
            canvas.setColorFill(new Color(238, 242, 255));
            canvas.rectangle(0, 0, document.getPageSize().getWidth(), document.getPageSize().getHeight());
            canvas.fill();


            // ######################### Adding Header ###################################
            // Our main aim is to put doctor name on the top left then the logo.
            // Firstly create one table to hold a table and a logo with only 1 entry
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            PdfPCell key = new PdfPCell();
            PdfPCell value = new PdfPCell();

            key.setPadding(6);
            key.setBorderWidth(0);
            key.setBorderColorTop(new Color(30, 58, 138));

            value.setPadding(6);
            value.setBorderWidth(0);

            Font keyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 15);

            keyFont.setColor(new Color(30, 58, 138));
            key.setPhrase(new Phrase("Dr. " + prescription.getDoctorName(), keyFont));
            headerTable.addCell(key);

            key.setPhrase(new Phrase(" ", keyFont));
            headerTable.addCell(key);

            keyFont.setColor(Color.black);
            key.setPhrase(new Phrase("Doctor Id: " + prescription.getDoctorId(), keyFont));
            headerTable.addCell(key);

            key.setPhrase(new Phrase("", keyFont));
            headerTable.addCell(key);

            key.setPhrase(new Phrase("", keyFont));
            headerTable.addCell(key);


//            headerTable.addCell(subHeaderTable);
            document.add(headerTable);

            // ================================ Trying to add image to the pdf ==================================
            Image logo = Image.getInstance("src/main/java/com/shield/eaarogya/Service/ServiceImpl/GradientLogo.png");
            logo.scaleAbsolute(150, 100);
            logo.setAbsolutePosition(400, 715);
            document.add(logo);

            // ################################ Trying to Create Table for patient details ###################################

            // ---------------------------------- Adding Patient Details -------------------------------
            PdfPTable patientTable = new PdfPTable(3);

            patientTable.setWidthPercentage(100);
            patientTable.setSpacingBefore(35);
            patientTable.setHorizontalAlignment(0);

            key = new PdfPCell();
            value = new PdfPCell();

            key.setPadding(6);
            key.setBorderWidth(0);

            value.setPadding(6);
            value.setBorderWidth(0);

            keyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
            valueFont = FontFactory.getFont(FontFactory.HELVETICA, 15);

            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("Date: " + prescription.getConsultationDate(), valueFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("Patient Id:", keyFont));
            patientTable.addCell(key);
            value.setPhrase(new Phrase("" + prescription.getPatientId(), valueFont));
            patientTable.addCell(value);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("Patient Name:", keyFont));
            patientTable.addCell(key);
            value.setPhrase(new Phrase(prescription.getPatientName(), valueFont));
            patientTable.addCell(value);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);



            key.setPhrase(new Phrase("Gender:", keyFont));
            patientTable.addCell(key);
            value.setPhrase(new Phrase(patient.getGender(), valueFont));
            patientTable.addCell(value);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            key.setPhrase(new Phrase("Contact Number:", keyFont));
            patientTable.addCell(key);
            value.setPhrase(new Phrase("" + patient.getPhoneNo(), valueFont));
            patientTable.addCell(value);
            key.setPhrase(new Phrase("", keyFont));
            patientTable.addCell(key);

            document.add(patientTable);

            // ---------------------------------- Patient Details Added ------------------------------------------

            // ---------------------------------- Adding Prescription ------------------------------------------

            PdfPTable observationTable = new PdfPTable(1);
            observationTable.setWidthPercentage(100);
            observationTable.setSpacingBefore(15);
            observationTable.setHorizontalAlignment(0);


            PdfPCell headerCell = new PdfPCell();
            PdfPCell paraCell = new PdfPCell();

            // Styling header cell
            headerCell.setBackgroundColor(Color.LIGHT_GRAY);
            headerCell.setBorderWidth(0);
            headerCell.setPadding(5);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            font.setColor(Color.BLACK);

            // Styling paragraph cell
            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 14);
            paraCell.setPadding(6);
            paraCell.setBorder(0);
            paraCell.setBorderWidth(0);

            headerCell.setPhrase(new Phrase("Observation", font));
            observationTable.addCell(headerCell);

            paraCell.setPhrase(new Phrase(prescription.getObservation(), paraFont));
            observationTable.addCell(paraCell);

            document.add(observationTable);

            PdfPTable medicineTable = new PdfPTable(2);
            medicineTable.setWidthPercentage(100);
            medicineTable.setSpacingBefore(15);
            medicineTable.setHorizontalAlignment(0);
            medicineTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            headerCell.setPhrase(new Phrase("Medicine", font));
            headerCell.setBorder(0);
            medicineTable.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Dosage", font));
            medicineTable.addCell(headerCell);

            // ----------------------------- Trying to add multiple medicines in a row -------------------------------
            Set<String> medicines = prescription.getMedicine();
            for(String medicine: medicines) {
                String[] dosage = medicine.split("-->");
                paraCell.setPhrase(new Phrase("" + dosage[0], paraFont));
                medicineTable.addCell(paraCell);
                paraCell.setPhrase(new Phrase("" + dosage[1], paraFont));
                medicineTable.addCell(paraCell);
            }

            document.add(medicineTable);

            PdfPTable remarkTable = new PdfPTable(1);
            remarkTable.setWidthPercentage(100);
            remarkTable.setSpacingBefore(15);
            remarkTable.setHorizontalAlignment(0);

            headerCell.setPhrase(new Phrase("Remark", font));
            remarkTable.addCell(headerCell);

            paraCell.setPhrase(new Phrase(prescription.getRemark(), paraFont));
            remarkTable.addCell(paraCell);

            document.add(remarkTable);

            // Close the document
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            System.out.println("Error Occurred in creating prescription pdf");
            e.printStackTrace();
            return null;
        }
    }
}
