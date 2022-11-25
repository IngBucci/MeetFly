package sample.view;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters;
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult;
import com.esri.arcgisruntime.tasks.geocode.LocatorTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.mysql.Utenti;
import sample.mysql.UtentiHasVoli;
import sample.mysql.UtentiHasVoliMYSQL;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Mappe {

    private UtentiHasVoli uhv;
    private Utenti utente;
    private Stage primaryStage = new Stage( );

    public void initMappe(Utenti utente, UtentiHasVoli uhv) throws SQLException {
            this.uhv = uhv;
            this.utente = utente;

            StackPane stackPane = new StackPane( );
            Scene scene = new Scene( stackPane );

            primaryStage.setTitle("Mappe");
            primaryStage.setScene( scene );
            primaryStage.show();

            //Creaiamo un ArcGISMap
            ArcGISMap map = new ArcGISMap( Basemap.createTopographicVector());

            //Creiamo una mapview
            mapView = new MapView();
            stackPane.getChildren().add(mapView);
            createLocatorTaskWithParameters();
            setupMap(15);
            setupTextField();
            setupTextField2();
            stackPane.getChildren().add(searchBox);
            StackPane.setAlignment(searchBox, Pos.TOP_LEFT);
            StackPane.setMargin(searchBox, new Insets(10, 0, 0, 10));
            stackPane.getChildren().add(searchBox2);
            StackPane.setAlignment(searchBox2, Pos.TOP_LEFT);
            StackPane.setMargin(searchBox2, new Insets(40, 0, 0, 10));

            setupGraphicsOverlay();
    }

    public void mappe(){

        StackPane stackPane = new StackPane( );
        Scene scene = new Scene( stackPane );

        primaryStage.setTitle("Mappe");
        primaryStage.setScene( scene );
        primaryStage.show();

        //Creaiamo un ArcGISMap
        ArcGISMap map = new ArcGISMap( Basemap.createTopographicVector());

        //Creiamo una mapview
        mapView = new MapView();
        stackPane.getChildren().add(mapView);
        createLocatorTaskWithParameters();
        setupMap(11);
    }

    private final int hexRed = 0xFFFF0000;
    private final int hexGreen = 0xFF41A317;

    private GraphicsOverlay graphicsOverlay;

    private TextField searchBox;
    private TextField searchBox2;
    private GeocodeParameters geocodeParameters;
    private LocatorTask locatorTask;

    private MapView mapView;

    private void setupMap(int i) {
        if (mapView != null) {
            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
            double latitude = 41.9109;
            double longitude = 12.4818;
            int levelOfDetail = i;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mapView.setMap(map);
        }
    }

    private void setupGraphicsOverlay() {
        if (mapView != null) {
            graphicsOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(graphicsOverlay);
        }
    }

    private void setupTextField() {
        searchBox = new TextField();
        searchBox.setMaxWidth(300);
        searchBox.setPromptText("Search for an address");
        searchBox.setText( uhv.getIndirizzoDestinazione()+", "+uhv.getCitta() );
        searchBox.setDisable( true );

        String query = searchBox.getText();
        if (!"".equals(query)) {
            geocodeQuery(query);
        }
    }

    private void setupTextField2() throws SQLException {
        searchBox2 = new TextField();
        searchBox2.setMaxWidth(300);
        searchBox2.setPromptText("Search for an address");
        UtentiHasVoliMYSQL uhvMySQL = new UtentiHasVoliMYSQL();
        String indirizzo = uhvMySQL.inidirizzo(utente.getId());
        System.out.println( indirizzo );
        searchBox2.setText( indirizzo );
        searchBox2.setDisable( true );

        String query2 = searchBox2.getText();
        if (!"".equals(query2)) {
            geocodeQuery2(query2);
        }
    }

    private void createLocatorTaskWithParameters() {
        locatorTask = new LocatorTask("http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer");
        geocodeParameters = new GeocodeParameters();
        geocodeParameters.getResultAttributeNames().add("*"); // return all attributes
        geocodeParameters.setMaxResults(1); // get closest match
        geocodeParameters.setOutputSpatialReference(mapView.getSpatialReference());
    }

    private void geocodeQuery(String query) {
        ListenableFuture<List<GeocodeResult>> geocode = locatorTask.geocodeAsync(query, geocodeParameters);

        geocode.addDoneListener(() -> {
            try {
                List<GeocodeResult> results = geocode.get();
                if (results.size() > 0) {
                    GeocodeResult result = results.get(0);
                    displayResult(result);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No results found.");
                    alert.show();
                }
            } catch (InterruptedException | ExecutionException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error getting result.");
                alert.show();
            }
        });
    }

    private void geocodeQuery2(String query) {
        ListenableFuture<List<GeocodeResult>> geocode = locatorTask.geocodeAsync(query, geocodeParameters);

        geocode.addDoneListener(() -> {
            try {
                List<GeocodeResult> results = geocode.get();
                if (results.size() > 0) {
                    GeocodeResult result = results.get(0);
                    displayResult2(result);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No results found.");
                    alert.show();
                }
            } catch (InterruptedException | ExecutionException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error getting result.");
                alert.show();
            }
        });
    }
    private void displayResult(GeocodeResult geocodeResult) {

        String label = geocodeResult.getLabel();
        TextSymbol textSymbol = new TextSymbol(15, label, hexRed, TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.BOTTOM);
        Graphic textGraphic = new Graphic(geocodeResult.getDisplayLocation(), textSymbol);
        SimpleMarkerSymbol markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, hexRed, 10.0f);
        Graphic markerGraphic = new Graphic(geocodeResult.getDisplayLocation(), geocodeResult.getAttributes(), markerSymbol);
        graphicsOverlay.getGraphics().addAll( Arrays.asList(markerGraphic, textGraphic));

        mapView.setViewpointCenterAsync(geocodeResult.getDisplayLocation());
        System.out.println( "Geocode Location: "+ geocodeResult.getDisplayLocation() );
        Point point = geocodeResult.getDisplayLocation();
        System.out.println( point );
    }

    private void displayResult2(GeocodeResult geocodeResult) {

        String label = geocodeResult.getLabel();
        TextSymbol textSymbol = new TextSymbol(15, label, 0Xff4AA02C, TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.BOTTOM);
        Graphic textGraphic = new Graphic(geocodeResult.getDisplayLocation(), textSymbol);
        SimpleMarkerSymbol markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, hexGreen, 10.0f);
        Graphic markerGraphic = new Graphic(geocodeResult.getDisplayLocation(), geocodeResult.getAttributes(), markerSymbol);
        graphicsOverlay.getGraphics().addAll( Arrays.asList(markerGraphic, textGraphic));

        mapView.setViewpointCenterAsync(geocodeResult.getDisplayLocation());
        System.out.println( "Geocode Location2: "+ geocodeResult.getDisplayLocation() );
        Point point = geocodeResult.getDisplayLocation();
        System.out.println("Point 2: " + point );
    }
}
