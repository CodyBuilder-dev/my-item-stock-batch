package com.example.myitemstockbatch.springbatch.repository;

import com.example.myitemstockbatch.springbatch.exception.MultipleLwstPrcException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class DanawaCrawlRepository {
    public long crawlDanawaMinPrice(long danawaId) throws IOException {
        String targetUrl = String.format("http://prod.danawa.com/info/?pcode=%d",danawaId);

        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // HTML appending
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // HTML Parsing
        Document doc = Jsoup.parse(response.toString());
        Elements lwst_prc = doc.select("a.lwst_prc > em");
        if (lwst_prc.size() > 1) {
            System.out.println(lwst_prc);
            throw new MultipleLwstPrcException("최저가가 한개가 아닙니다.");
        }
        String lwst_prc_val = lwst_prc.text().replace(",","");

        return Long.parseLong(lwst_prc_val);
    }
}
