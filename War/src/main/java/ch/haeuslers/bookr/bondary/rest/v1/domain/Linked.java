package ch.haeuslers.bookr.bondary.rest.v1.domain;


import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

public abstract class Linked {

    protected List<Link> links;

    @XmlElementRef
    public List<Link> getLinks()
    {
        return links;
    }

    public void setLinks(List<Link> links)
    {
        this.links = links;
    }

    @XmlTransient
    public String getNext()
    {
        if (links == null) return null;
        for (Link link : links)
        {
            if ("next".equals(link.getRelationship())) return link.getHref();
        }
        return null;
    }

    @XmlTransient
    public String getPrevious()
    {
        if (links == null) return null;
        for (Link link : links)
        {
            if ("previous".equals(link.getRelationship())) return link.getHref();
        }
        return null;
    }
}
