<?php
        $lat = 0;
        $lng = 0;
        $r = 0;
        $loadMap = false;
    if (isset($_GET['lat'], $_GET['lng'])) {
        if (isset($_GET['lat'])) $lat = $_GET['lat'];
        if (isset($_GET['lng'])) $lng = $_GET['lng'];
        if (isset($_GET['r'])) $r = $_GET['r'];
        $loadMap = true;
    }

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>ClickTracker by MBEntwicklung</title>
<link rel="stylesheet" type="text/css" href="/template/template.css" />
<link rel="icon" href="/template/grafiken/favicon.jpg" type="image/x-icon" />
<link rel="image_src" href="http://clicktracker.mb-entwicklung.de/files/clicktracker.png" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<!-- Facebook Vorschau Bild -->
<meta name="og:image" content="http://clicktracker.mb-entwicklung.de/files/clicktracker.png" />

<script src="http://www.openlayers.org/api/OpenLayers.js" type="text/javascript" ></script>
<script type="text/javascript">
  function initialize() {
    var map = new OpenLayers.Map("map");
    map.addLayer(new OpenLayers.Layer.OSM());
    
    var lonLat = new OpenLayers.LonLat(<?php echo $lng;  ?> , <?php echo $lat; ?> )
          .transform(
            new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
            map.getProjectionObject() // to Spherical Mercator Projection
          );
 
    var zoom=14;
    
    var markers = new OpenLayers.Layer.Markers( "Markers" );
    map.addLayer(markers);
 
    markers.addMarker(new OpenLayers.Marker(lonLat));
 
    map.setCenter (lonLat, zoom);
  }

</script>
</head>

<body class="body" onload="initialize()">
    <div class="container">

        <div class="head">
			<div class="nav2">
                <a href="http://clicktracker.mb-entwicklung.de/">Home</a> &nbsp;&nbsp;&nbsp; 
                <a href="http://www.mb-entwicklung.de/">Entwickler</a> &nbsp;&nbsp;&nbsp; 
                <a href="http://www.mb-entwicklung.de/impressum">Impressum</a> &nbsp;&nbsp;&nbsp; 
                <a href="http://www.mb-entwicklung.de/kontakt">Kontakt</a></div>
		</div>

		<div class="shadowright"></div>

		<div class="content">
<?php

if ($loadMap) {
    include 'map.php';
} else {
    include 'home.php';
}
?>

            <br /><br />
		</div>
	</div>

	<div class="ground">
        <div class="textfeldfooter">
            <i>ClickTracker</i> is a small Android App for send the position with one click.
			<p>
			    <a href="http://validator.w3.org/check?uri=referer">
				<img  style="border:0;width:88px;height:31px"
				    src="http://www.w3.org/Icons/valid-xhtml10"
				    alt="Valid XHTML 1.0 Transitional" /></a>

			    <a href="http://jigsaw.w3.org/css-validator/check/referer">
				<img style="border:0;width:88px;height:31px"
				    src="http://jigsaw.w3.org/css-validator/images/vcss"
				    alt="CSS ist valide!" />
			    </a>
			</p>
		</div>
	</div>

	<script type="text/javascript">
	var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-4181414-18']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();</script>

</body>
</html>