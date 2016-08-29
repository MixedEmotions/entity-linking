package ie.nuig.entitylinking.main.nel;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.nuig.entitylinking.core.AnnotatedMention;
import ie.nuig.entitylinking.core.ELDocument;
import ie.nuig.entitylinking.core.Pair;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author John McCrae <john@mccr.ae>
 */
public class EntityLinkingServer {

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Please specify configuration");
                System.exit(-1);
            }
            final EntityLinkingMain entityLinkingMain = new EntityLinkingMain(args[0]);
            final ObjectMapper mapper = new ObjectMapper();

            Server server = new Server(5000);
            server.setHandler(new AbstractHandler() {

                @Override
                public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                    NIFInput input = mapper.readValue(request.getInputStream(), NIFInput.class);
                    NIFOutput output = new NIFOutput();

                    output.setId(input.getId());
                    
                    List<NIFOutput.Entry> entries = new ArrayList<NIFOutput.Entry>();
                    
                    for(NIFInput.Entry e : input.getEntries()) {
                        NIFOutput.Entry oe = new NIFOutput.Entry();
                        oe.setId(e.getId());
                        List<NIFOutput.Entity> entities = new ArrayList<NIFOutput.Entity>();
                        
                        ELDocument elDocument = new ELDocument(e.getIsString(), null);

                        entityLinkingMain.processDocument(elDocument);

                        for(AnnotatedMention mention : elDocument.getAnnotatedMention()) {
                            NIFOutput.Entity na = new NIFOutput.Entity();
                            String string = mention.getMention();
                            int beginIndex = e.getIsString().indexOf(string);
                            if(beginIndex < 0) {
                                throw new RuntimeException(string + " was not in " + e.getIsString());
                            }
                            int endIndex = beginIndex + string.length();
                            na.setId(e.getId() + "#char=" + beginIndex + "," + endIndex);
                            na.setBeginIndex(beginIndex);
                            na.setEndIndex(endIndex);
                            na.setAnchorOf(string);
                            List<Pair<String, Double>> linkScorePairs = mention.getRankedListCandidates();
                            if(linkScorePairs.size() != 1) {
                                throw new RuntimeException("More than one link/score pair");
                            }
                            na.setReferences(linkScorePairs.get(0).getKey());
                            na.setScore(linkScorePairs.get(0).getValue());

                            entities.add(na);
                        }

                        oe.setEntities(entities);
                        entries.add(oe);
                    }
                    output.setEntries(entries);

                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        mapper.writeValue(out, output);
                    } finally {
                        if(out != null) {
                            out.flush();
                            out.close();
                        }
                    }
                    
                }

            });
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
