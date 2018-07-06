'use strict';


/* =======================================================================
                    Form validation
========================================================================*/




window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
        form.addEventListener('submit', function(event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
}, false);


/* =======================================================================
                    Autocomplete search bar API
========================================================================*/


$(function() {
    function log( message ) {
        $( "<div>" ).text( message ).prependTo( "#log" );
        $( "#log" ).scrollTop( 0 );
    }
    $( ".airports" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "https://api.sandbox.amadeus.com/v1.2/airports/autocomplete",
                dataType: "json",
                data: {


                    apikey: "5IomxX3j0OOD87Um4X9aTZdAgnttyJG0",

                    term: request.term
                },
                success: function( data ) {
                    response( data );
                }
            });
        },
        minLength: 3,
        select: function( event, ui ) {
            log( ui.item ?
                "Selected: " + ui.item.label :
                "Nothing selected, input was " + this.value);
        },
        open: function() {
            $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
        },
        close: function() {
            $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
        }
    });
});



/* =======================================================================
                    Low flight API
========================================================================*/

$(function () {


        document.getElementById("lowFlight").addEventListener('click', function () {


            var origin = "&origin=" + $("#origin").val();

            var destination = "&destination=" + $("#destination").val();

            var departure_date = "&departure_date=" + $("#departure").val();

            var return_date = "&return_date=" + $("#returnDate").val();

            var maximumPrice = "&max_price=" + $("#price").val();

            var currency = "&currency=USD" ;


            var api_key = '5IomxX3j0OOD87Um4X9aTZdAgnttyJG0';


            // if(return_date.val() == null) {
            //     var resource_url = 'https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=' + api_key + origin + destination + departure_date + maximumPrice + currency;
            // } else {
               var resource_url = 'https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=' + api_key + origin + destination + departure_date + return_date + maximumPrice + currency;

            // }

            var request=$.get(resource_url);
            request.fail(function (current, status, error) {
                console.log(status);
                console.log(error);
            });

            console.log(request);
            request.done(function (response) {
                //Since we are calling data out of the same array we can create a loop to put the machine to work
                main(response);
            });

            function main(response) {

                console.log(response);
                //this loop will iterate through the layers of data
                for (var r = 0; r < 50; r++){
                    // console.log(response.results[r]);

                    // $("#solo").html("<div class='card-body text-center'>" + response.results[r] + "\xB0" + "</div>");

                }
            }

            main();

        });

});


/* =======================================================================
                    POI Map
========================================================================*/


$(function () {

    document.getElementById("ent").addEventListener('click', function () {



//building the map

// var location={ lat: 19.775221,
//     lng: -99.885829}



        var mapOptions = {
            zoom: 12,
            center: {
                lat: 42.343794,
                lng:-71.067170
            },
            mapTypeId: google.maps.MapTypeId.TERRAIN,
            draggable: true
        };
        var mapCanvas = document.getElementById('poiMap');
        var map = new google.maps.Map(mapCanvas, mapOptions);


//setting the main marker ...should be the user destination






        function setMarker(location) {
            marker.setPosition(location);
        }

        marker  = new google.maps.Marker({
            position: {
                lat: 42.3437941,
                lng:-71.067170
            },
            map: map
        });


        function setMarker(location) {
            marker.setPosition(location);

        }




// function to get a marker for each poi on the JSON
        function poiToMarker( data)
        {
            for (var i = 0; i < data.points_of_interest.length; i++) {
                marker = new google.maps.Marker({
                    position: {
                        lat: data.points_of_interest[i].location.latitude,
                        lng: data.points_of_interest[i].location.longitude
                    },
                    map: map
                });
                $("#poiPosts").append(createReport(data.points_of_interest[i]));

            }


        }


// obtain the Poi JSON  passing a location and setting the markers for the destination


        $.get('https://api.sandbox.amadeus.com/v1.2/points-of-interest/yapq-search-circle?',
            {
                dataType: 'json',
                apikey:'TXIA8oaPoUrPTKogMt0y496orBf38IqM',
                // apikey: '8XVJrEZAxpKF0vkSXh9HGpKGVAxVEGhD',
                // apikey:'v6aAEJ4TnGgvIJhW6cKrX7wW0hCVwsfy',
                // apikey:'5IomxX3j0OOD87Um4X9aTZdAgnttyJG0',
                latitude:  42.343794,
                longitude:  -71.067170,
                radius: '20'
            }).done(function (data) {
            console.log(data);
            poiToMarker(data);
        });


//creating the div for the view

        function createReport(poi) {
            var htmlPlace="";
            htmlPlace+="<div class='screen'>";
            htmlPlace+="<h3>"+poi.title+"</h3>"+"\n";
            htmlPlace+="<img src="+poi.main_image+">"+"\n";
            htmlPlace+="<p> "+poi.details.description+"</p>"+"\n";
            htmlPlace+="</div>";
            return htmlPlace
        }





    });

});








