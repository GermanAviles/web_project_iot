<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Graficar datos</title>
</head>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>



<script>
$.getJSON( "http://localhost:8080/web_proyect_iot/rest/service/json", function( data ) {


}).done(function (data) {

	var irisSetosa;
	var irisVersicolor;
	var irisVirginica;

	for(i=0; i<data.length; i++) {

		if(data[i].categoria == "Iris-setosa"){

			irisSetosa = [data[i].sl, data[i].sw, data[i].pl, data[i].pw];

			//alert(data[i].pw);
		}

		if(data[i].categoria == "Iris-versicolor"){
			irisVersicolor = [data[i].sl, data[i].sw, data[i].pl, data[i].pw];
		}

		if(data[i].categoria == "Iris-virginica"){
			irisVirginica = [data[i].sl, data[i].sw, data[i].pl, data[i].pw];
		}
	}


Highcharts.chart('container', {
    chart: {
        type: 'column'
    },
    title: {
        text: 'Graficando datos, Arduino-python-Java-Mongo'
    },
    xAxis: {
        categories: [
            'Iris-Setosa',
            'Iris-Vergicolor',
            'Iris-Virginica'
        ]
    },
    yAxis: [{
        min: 0,
        title: {
            text: 'Datos'
        }
    }, {
        title: {
            text: 'Datos'
        },
        opposite: true
    }],
    legend: {
        shadow: false
    },
    tooltip: {
        shared: true
    },
    plotOptions: {
        column: {
            grouping: false,
            shadow: false,
            borderWidth: 0
        }
    },
    series: [{
        name: 'Sepal-width',
        color: 'rgba(165,170,217,1)',
        data: [irisSetosa[1], irisVersicolor[1], irisVirginica[1]],
        
        pointPadding: 0.3,
        pointPlacement: -0.2
    }, {
        name: 'Sepal-length',
        color: 'rgba(126,86,134,.9)',
        data: [irisSetosa[0], irisVersicolor[0], irisVirginica[0]],
        pointPadding: 0.4,
        pointPlacement: -0.2
    }, {
        name: 'Petal-widht',
        color: 'rgba(248,161,63,1)',
        data: [irisSetosa[3], irisVersicolor[3], irisVirginica[3]],
        //data: [1, 1, 2],
        
        pointPadding: 0.3,
        pointPlacement: 0.2,
        yAxis: 1
    }, {
        name: 'Petal-length',
        color: 'rgba(186,60,61,.9)',
        data: [irisSetosa[2], irisVersicolor[2], irisVirginica[2]],
        
        pointPadding: 0.4,
        pointPlacement: 0.2,
        yAxis: 1
    }]
});


});
</script>
<body>

</body>
</html>