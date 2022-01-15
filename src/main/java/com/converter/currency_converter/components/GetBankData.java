package com.converter.currency_converter.components;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.converter.currency_converter.models.Currency;
import com.converter.currency_converter.models.Rate;
import com.converter.currency_converter.repository.CurrencyRepository;
import com.converter.currency_converter.repository.RateRepository;

import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Component
public class GetBankData {

    @PostConstruct
    private void initNowData() {
        Date date = new Date(new java.util.Date().getTime());
        getDataByDate(date);
    }

    public void getDataByDate(Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db
                    .parse(new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + String.format("%02d", day)
                            + "/" + String.format("%02d", month) + "/" + year).openStream());

            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            RateRepository rateRepo = new RateRepository();
            if (rateRepo.existsByDate(date))
                return;

            CurrencyRepository curRepo = new CurrencyRepository();

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node valute = nodeList.item(i);

                String id = valute.getAttributes().getNamedItem("ID").getNodeValue();

                Currency currency = new Currency();
                Rate rate = new Rate();

                currency.setId(id);
                rate.setId(id);
                rate.setDate(date);

                NodeList parametres = valute.getChildNodes();
                for (int j = 0; j < parametres.getLength(); j++) {
                    switch (parametres.item(j).getNodeName()) {
                        case "NumCode":
                            currency.setNum_code(parametres.item(j).getTextContent());
                            break;
                        case "CharCode":
                            currency.setChar_code(parametres.item(j).getTextContent());
                            break;
                        case "Nominal":
                            rate.setNominal(Integer.parseInt(parametres.item(j).getTextContent()));
                            break;
                        case "Name":
                            currency.setName(parametres.item(j).getTextContent());
                            break;
                        case "Value":
                            rate.setRate(Float.parseFloat(parametres.item(j).getTextContent().replace(',', '.')));
                            break;
                    }
                }

                currency.setRate(rate);
                curRepo.save(currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (DOMException e) {
            e.printStackTrace();
        }
    }
}
