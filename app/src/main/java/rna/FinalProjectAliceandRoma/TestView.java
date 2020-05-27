package rna.FinalProjectAliceandRoma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TestView extends AppCompatActivity {

    TextView question;
    RadioGroup radioGroup;
    RadioButton firstOption;
    RadioButton secondOption;
    RadioButton thirdOption;
    RadioButton fourthOption;
    Button next;
    int count = 0;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        init();

        final List<String> questions = new ArrayList<>();
        List<String> options = new ArrayList<>();
        List<String> userAnswers = new ArrayList<>();
        List<String> rightAnswers = new ArrayList<>();

        question = (TextView) findViewById(R.id.questionView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        firstOption = (RadioButton) findViewById(R.id.firstOption);
        secondOption = (RadioButton) findViewById(R.id.secondOption);
        thirdOption = (RadioButton) findViewById(R.id.thirdOption);
        fourthOption = (RadioButton) findViewById(R.id.fourthOption);
        next = (Button) findViewById(R.id.button_next);

        InputStream inputStream = null;
        try {
            inputStream = getAssets().open(getIntent().getExtras().getString("title") + ".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        Element element = document.getDocumentElement();
        element.normalize();

        NodeList nodeList = document.getElementsByTagName("question");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String question = element1.getTextContent();

                questions.add(question);

            }

        }

        nodeList = document.getElementsByTagName("option");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String option = element1.getTextContent();

                options.add(option);

            }

        }

        nodeList = document.getElementsByTagName("rightAnswer");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String rightAnswer = element1.getTextContent();

                rightAnswers.add(rightAnswer);

            }

        }

        question.setText(questions.get(0));

        firstOption.setText(options.get(0));
        secondOption.setText(options.get(1));
        thirdOption.setText(options.get(2));
        fourthOption.setText(options.get(3));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count ++;

                if (count == 1) {

                    question.setText(questions.get(1));

                } else if (count == 2) {

                    question.setText(questions.get(2));

                } else if (count == 3) {

                    question.setText(questions.get(3));

                } else if (count == 4) {

                    question.setText(questions.get(4));

                } else if (count == 5) {

                    Intent i = new Intent(TestView.this, TestResult.class);
                    i.putExtra("result", "100%");
                    startActivity(i);

                }
            }
        });

    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
