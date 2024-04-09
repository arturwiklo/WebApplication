package com.example.runnerz.run;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    private final RunRepository runRepository;

    public DataInitializer(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    public void initializeDataFromXml() {
        try {
            ClassPathResource resource = new ClassPathResource("data.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(resource.getInputStream());

            NodeList runNodes = document.getElementsByTagName("run");

            List<Run> runs = new ArrayList<>();

            for (int i = 0; i < runNodes.getLength(); i++) {
                Element runElement = (Element) runNodes.item(i);
                Integer id = Integer.parseInt(runElement.getElementsByTagName("id").item(0).getTextContent());
                String title = runElement.getElementsByTagName("title").item(0).getTextContent();
                Instant startedOn = Instant.parse(runElement.getElementsByTagName("startedOn").item(0).getTextContent());
                Instant completedOn = Instant.parse(runElement.getElementsByTagName("completedOn").item(0).getTextContent());
                Integer kilometers = Integer.parseInt(runElement.getElementsByTagName("kilometers").item(0).getTextContent());
                Location location = Location.valueOf(runElement.getElementsByTagName("location").item(0).getTextContent());

                runs.add(new Run(id, title, startedOn, completedOn, kilometers, location));
            }

            runRepository.saveAll(runs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}