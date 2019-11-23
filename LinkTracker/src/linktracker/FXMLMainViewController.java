package linktracker;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.WebPage;
import utils.FileUtils;
import utils.LinkReader;
import utils.MessageUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class FXMLMainViewController {

    @FXML
    private MenuItem menuLoadFile;

    @FXML
    private MenuItem menuExit;

    @FXML
    private MenuItem menuStart;

    @FXML
    private MenuItem menuClear;

    @FXML
    private Label totalPages;

    @FXML
    private Label totalProcessed;

    @FXML
    private Label totalLinks;

    @FXML
    private ListView<String> webpageListview;

    @FXML
    private ListView<String> linkListview;

    private List<WebPage> webpages;
    private List<String> links;

    @FXML
    void initialize() {
        assert menuLoadFile != null : "fx:id=\"menuLoadFile\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert menuExit != null : "fx:id=\"menuExit\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert menuStart != null : "fx:id=\"menuStart\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert menuClear != null : "fx:id=\"menuClear\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert webpageListview != null : "fx:id=\"webpageList\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert totalPages != null : "fx:id=\"totalPages\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert totalProcessed != null : "fx:id=\"totalProcessed\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert totalLinks != null : "fx:id=\"totalLinks\" was not injected: check your FXML file 'FXMLMainView.fxml'.";
        assert linkListview != null : "fx:id=\"linkList\" was not injected: check your FXML file 'FXMLMainView.fxml'.";

        webpages = new ArrayList<WebPage>();
        links = new ArrayList<String>();
    }

    public void loadData(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        Path path;
        if (file != null)
            path = file.toPath();
        else
            path = null;

        webpages = FileUtils.loadPages(path);

        if (webpages != null)
        {
            ObservableList<String> webNamesList = FXCollections.observableArrayList();
            for (WebPage w: webpages) {
                webNamesList.add(w.getName());
            }
            totalPages.setText(String.valueOf(webpages.size()));
            webpageListview.setItems(webNamesList);

            utils.MessageUtils.showMessage(
                    webpageListview.getItems().size() + " urls loaded");
        }
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void start(ActionEvent actionEvent) {

        if (webpages.size() > 0)
        {
            try{
                ThreadPoolExecutor manager = (ThreadPoolExecutor)Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());

                System.out.println("Start");
                List<Future<WebPage>> futures =
                    webpages.stream().map(
                        strm -> manager.submit(getCallable(strm))).collect(Collectors.toList()
                );

                System.out.println("Finish");

            }catch (Exception e){}
        }
        else
        {
            utils.MessageUtils.showError("No URL file loaded");
        }
    }

    public void clear(ActionEvent actionEvent) {
        linkListview.setItems(null);
        webpages.clear();
        webpageListview.setItems(null);
        totalLinks.setText("0");
        totalPages.setText("0");
        totalProcessed.setText("0");
    }

    public void selectWeb(MouseEvent mouseEvent) {
        int wp = webpageListview.getSelectionModel().getSelectedIndex();

        if (wp >= 0)
            links = webpages.get(wp).getLniksInside();
    }

    public Callable<WebPage> getCallable(WebPage web)
    {
        return () -> {
            try
            {
                web.setLniksInside(LinkReader.getLinks(web.getUrl()));
            } catch (Exception e) {
                utils.MessageUtils.showError("Error while loading the urls");
            }
            return null;
        };
    }

}
