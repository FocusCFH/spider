package org.example.spider.test.tianyancha;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Example_01
 * @Description
 * @Author chenfuhao
 * @Date 2021/9/13 17:26
 * @Version 1.0
 **/
public class Example_01 {
    @Data
    static
    class CompanyModel {
        /**
         * 公司名称
         */
        private String name;
        /**
         * 城市
         */
        private String city;
        /**
         * 法定代表人
         */
        private String legalRepresentative;

        /**
         * 成立日期
         */
        private String createDate;

        /**
         * 注册资本
         */
        private String registerCapital;

        /**
         * 链接
         */
        private String url;

    }

    /**
     * 解析当前页的记录
     * @param html
     */
    public static List<CompanyModel> analysisCurrentPage(String html){
        Document doc = Jsoup.parse(html);
        // 内容div
        Elements resultList = doc.getElementsByClass("result-list sv-search-container");
        if (CollectionUtils.isEmpty(resultList)) return null;
        List<CompanyModel> modelList = new ArrayList<>();
        Element resultContent  = resultList.get(0);
        Elements childs = resultContent.children();
//        System.out.println("总记录数：" + childs.size());
        for (int i = 0; i < childs.size(); i++) {
            CompanyModel model = new CompanyModel();
            Element searchResultSingle = doc.getElementsByClass("search-result-single").get(i);
            Element content = searchResultSingle.getElementsByClass("content").get(0);
            Element header = content.getElementsByClass("header").get(0);
            Element headerHref = header.getElementsByTag("a").get(0);
            String name = header.text();
            String url = headerHref.attr("href");
            Elements citySpan = searchResultSingle.getElementsByClass("site");
            String city = citySpan.get(0).text();
            model.setName(name);
            model.setUrl(url);
            model.setCity(city);
            Elements  textEllipsis = content.getElementsByClass("info row text-ellipsis");
            for (int textEllipsisIndex = 0; textEllipsisIndex < textEllipsis.size();textEllipsisIndex++){
                Element e = textEllipsis.get(textEllipsisIndex);
                // -------取法定个代表人
                Element legalRepresentativeDiv = e.getElementsByClass("title -wider text-ellipsis").get(0);
                // 有a标签取标签，没有取span标签
                Elements a1 = legalRepresentativeDiv.getElementsByTag("a");
                String legalRepresentative = "-";
                if (!CollectionUtils.isEmpty(a1)) legalRepresentative = a1.get(0).text();
                // -------取注册资本
                Element registerCapitalDiv = e.getElementsByClass("title -narrow text-ellipsis").get(0);
                // 有a标签取标签，没有取span标签
                Elements a2 = registerCapitalDiv.getElementsByTag("span");
                String registerCapital = "-";
                if (!CollectionUtils.isEmpty(a2)) registerCapital = a2.get(0).text();
                // -------取成立日期
                Element createDateDiv = e.getElementsByClass("title  text-ellipsis").get(0);
                // 有a标签取标签，没有取span标签
                Elements a3 = createDateDiv.getElementsByTag("span");
                String createDate = "-";
                if (!CollectionUtils.isEmpty(a3)) createDate = a3.get(0).text();
                model.setLegalRepresentative(legalRepresentative);
                model.setRegisterCapital(registerCapital);
                model.setCreateDate(createDate);
            }
//            System.out.println("第 " + i + " 条 : " + JSONObject.toJSONString(model));
            modelList.add(model);
        }
        return modelList;
    }

    /**
     * 获取总分页数
     * @param html
     * @return
     */
    public static Integer getTotalPage(String html){
        int totalPage = 0;
        Document doc = Jsoup.parse(html);
        // 页脚div
        Elements resultFooter = doc.getElementsByClass("result-footer");
        if (CollectionUtils.isNotEmpty(resultFooter)) {
            // 获取总分页数
            Element e = resultFooter.get(0);
            Element resultFooterPager =  e.getElementsByClass("search-pager").get(0);
            Element customize = resultFooterPager.getElementById("customize");
            String totalPageStr = customize.getElementById("pg_customize_total").attr("value");
            totalPage = Integer.parseInt(totalPageStr);
            return totalPage;
        }
        return null;
    }

    /**
     * 爬虫入口
     * @throws IOException
     */
    public static String connectAndGetResult(String baseUri) throws IOException {
        URL url = new URL(baseUri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        conn.connect();
        System.out.println(conn.getResponseCode() + "   "  + conn.getResponseMessage());
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            String result = new String(IOUtils.toByteArray(inputStream));
            try {
                Thread.sleep(2000);    //延时2秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }
        return null;
    }
    public static void spiderTianYanCha() throws IOException{
        List<CompanyModel> result = new ArrayList<>();
        String baseUri =  "https://www.tianyancha.com/search";
        String html = connectAndGetResult(baseUri);
        if (html == null) return;
        Integer totalPage = getTotalPage(html);
        if (totalPage == null) return;
        for (int i = 0; i < totalPage; i++){
            html = connectAndGetResult(baseUri);
            if (html == null) {

                continue;
            }
            List<CompanyModel> modelList = analysisCurrentPage(html);
            if (CollectionUtils.isNotEmpty(modelList)) result.addAll(modelList);
        }
        System.out.println();
    }

    public static void main(String[] args ) throws IOException {
//        spiderTianYanCha();


        List<CompanyModel> cl = null;
        for (CompanyModel c :cl){
            System.out.println(c);
        }
    }

}
