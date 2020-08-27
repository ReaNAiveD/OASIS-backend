package com.nju.oasis.controller.VO;

import java.util.ArrayList;

public class CoworkerLinkVO {

    SimpleAuthor from;
    SimpleAuthor to;
    ArrayList<SimpleDocument> docs;

    public CoworkerLinkVO(SimpleAuthor from, SimpleAuthor to) {
        this.from = from;
        this.to = to;
        this.docs = new ArrayList<>();
    }

    public SimpleAuthor getFrom() {
        return from;
    }

    public void setFrom(SimpleAuthor from) {
        this.from = from;
    }

    public SimpleAuthor getTo() {
        return to;
    }

    public void setTo(SimpleAuthor to) {
        this.to = to;
    }

    public ArrayList<SimpleDocument> getDocs() {
        return docs;
    }

    public static class SimpleAuthor{
        int id;
        String name;
        int affiliationId;
        String affiliation;
        int docCount;
        double activation;

        public SimpleAuthor(int id, String name, int affiliationId, String affiliation, int docCount, double activation) {
            this.id = id;
            this.name = name;
            this.affiliationId = affiliationId;
            this.affiliation = affiliation;
            this.docCount = docCount;
            this.activation = activation;
        }

        public int getDocCount() {
            return docCount;
        }

        public double getActivation() {
            return activation;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAffiliationId() {
            return affiliationId;
        }

        public String getAffiliation() {
            return affiliation;
        }
    }
    public static class SimpleDocument{
        int id;
        String title;

        public SimpleDocument(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
