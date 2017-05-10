MixedEmotions Entity Linking Module
===================================

Installation with Maven 2:

    mvn install

Running
-------

Running the container:

    sudo docker pull mixedemotions/08_entity_extraction_pt
    sudo docker run -p 32769:2812 --name entities_pt -d mixedemotions/08_entity_extraction_pt

Check that the container “entities_pt” is running:

    sudo docker ps

You should see something like:

    CONTAINER ID     IMAGE                                                     COMMAND   CREATED      STATUS              
    fb204d4de6db        mixedemotions/08_entity_extraction_pt   "python3  4 weeks ago         Up 2 minutes        
    
    PORTS                     NAMES
    0.0.0.0:32769->2812/tcp   entities_pt
    
And example of concept extraction for the text “Cristiano ronaldo seguirá cuatro años más en el real madrid”

    http://localhost:32769/?text=Cristiano%20ronaldo%20seguir%C3%A1%20cuatro%20a%C3%B1os%20m%C3%A1s%20en%20el%20real%20madrid

You should receive something like: 

    {
      concepts: 
      [
        "real madrid",
        "cristiano ronaldo"
      ]
    }

