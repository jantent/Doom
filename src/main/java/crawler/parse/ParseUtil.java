package crawler.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

public class ParseUtil {

    public static void parseLinks(String html){
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        print("\nMedia: (%d)", media.size());
        Iterator var6 = media.iterator();

        Element link;
        while(var6.hasNext()) {
            link = (Element)var6.next();
            if (link.tagName().equals("img")) {
                print(" * %s: <%s> %sx%s (%s)", link.tagName(), link.attr("abs:src"), link.attr("width"), link.attr("height"), trim(link.attr("alt"), 20));
            } else {
                print(" * %s: <%s>", link.tagName(), link.attr("abs:src"));
            }
        }

        print("\nImports: (%d)", imports.size());
        var6 = imports.iterator();

        while(var6.hasNext()) {
            link = (Element)var6.next();
            print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        var6 = links.iterator();

        while(var6.hasNext()) {
            link = (Element)var6.next();
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        return s.length() > width ? s.substring(0, width - 1) + "." : s;
    }
}
