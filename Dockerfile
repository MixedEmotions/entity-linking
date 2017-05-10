FROM jeanblanchard/java:8
MAINTAINER john@mccr.ae

EXPOSE 5000

# Run mvn install first!
ADD ie.nuig.entitylinking.properties /
ADD ./main/target/main-0.0.1-SNAPSHOT-jar-with-dependencies.jar /entity-linking.jar
RUN wget http://server1.nlp.insight-centre.org/mixed-emotions-entity-linking/WikiLex_AnchorsPrior.tar.gz -O /english.all.3class.distsim.crf.ser.gz
RUN wget http://server1.nlp.insight-centre.org/mixed-emotions-entity-linking/english.all.3class.distsim.crf.ser.gz -O /english.all.3class.distsim.crf.ser.gz
#ADD WikiLex_AnchorsPrior.tar.gz /
#ADD english.all.3class.distsim.crf.ser.gz /

RUN tar xzvf /WikiLex_AnchorsPrior.tar.gz
CMD [ "/opt/jdk/bin/java", "-jar", "/entity-linking.jar", "/ie.nuig.entitylinking.properties" ]
