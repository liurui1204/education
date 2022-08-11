package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Helper;


import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity.*;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Config.*;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Decoder.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.views.DocumentView;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NJCommonHelper {
    private static SOAService cacheSoaService = new SOAService();

    public NJCommonHelper() {
    }

    /**
     * 初始化一个DocumentBuilder
     *
     * @return a DocumentBuilder
     * @throws ParserConfigurationException
     */
    public static DocumentBuilder newDocumentBuilder()
            throws ParserConfigurationException {
        return newDocumentBuilderFactory().newDocumentBuilder();
    }

    /**
     * 初始化一个DocumentBuilderFactory
     *
     * @return a DocumentBuilderFactory
     */
    public static DocumentBuilderFactory newDocumentBuilderFactory() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf;
    }

    public static void InitServiceList(String xmlString) throws Exception {
        File serviceFile = null;

//        try {
//            serviceFile = new File(xmlPath);
//        } catch (Exception var10) {
//            throw new Exception("服务配置文件不存在！");
//        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = newDocumentBuilder().parse(
                    new InputSource(new StringReader(xmlString)));
//            Document doc = builder.parse(serviceFile);
            NodeList node = doc.getElementsByTagName("certNo");
            if (node.getLength() < 1) {
                throw new Exception("配置文件中没有证书号配置节点！");
            } else {
                String certNo = node.item(0).getTextContent().trim();
                if ("".equals(certNo)) {
                    throw new Exception("配置文件中的证书号为空！");
                } else {
                    cacheSoaService.setCertNo(certNo);
                    NodeList nl = doc.getElementsByTagName("directService");

                    for(int i = 0; i < nl.getLength(); ++i) {
                        cacheSoaService.getDirectServices().add(new DirectService(nl.item(i).getAttributes().getNamedItem("name").getNodeValue(), nl.item(i).getAttributes().getNamedItem("url").getNodeValue()));
                    }

                    nl = doc.getElementsByTagName("busServices");
                    cacheSoaService.getBusService().setBusAddress(nl.item(0).getAttributes().getNamedItem("busUrl").getNodeValue());
                    NodeList busService = doc.getElementsByTagName("busService");

                    for(int i = 0; i < nl.getLength(); ++i) {
                        cacheSoaService.getBusService().getBusServices().add(busService.item(i).getAttributes().getNamedItem("name").getNodeValue());
                    }

                }
            }
        } catch (Exception var11) {
            throw new Exception("服务配置文件配置错误！" + var11.getMessage());
        }
    }

    public static NJRespMsgEntity GetSerInvoke(NJReqMsgEntity reqEntity) throws Exception {
        reqEntity.getNJReqHeaderEntity().setCertNo(cacheSoaService.getCertNo());
        NJRespMsgEntity respEntity = new NJRespMsgEntity();
        if (!ValidHeader(reqEntity, respEntity)) {
            return respEntity;
        } else {
            try {
                String strServiceID = reqEntity.getNJReqHeaderEntity().getServiceID();
                String strUrl = GetUrl(strServiceID);
                if (strUrl == null || "".equals(strUrl)) {
                    throw new Exception("找不到服务对应的地址！请查看服务配置文件！");
                }

                strUrl = strUrl + "/Invoke";
                reqEntity.getNJReqBodyEntity().setDataEntity(NJEncryptHelper.EncryptToBase64(reqEntity.getNJReqBodyEntity().getDataEntity()));
                String xmlEnti = NJXmlHelper.Serialize(reqEntity);
                String respXml = RESTInvoke(strUrl, xmlEnti);
                System.out.println(respXml);
                respEntity = (NJRespMsgEntity)NJXmlHelper.Deserialize(respXml, NJRespMsgEntity.class);
                if (respEntity.getRespHead().getResultCode().equals("200".toString())) {
                    String aaaString = respEntity.getRespBody().getDataResult();
                    String aString = NJEncryptHelper.DeEncryptFromBase64(aaaString).trim();
                    respEntity.getRespBody().setDataResult(aString);
                }
            } catch (Exception var8) {
                NJRespMsgHead header = new NJRespMsgHead();
                NJRespMsgBody body = new NJRespMsgBody();
                respEntity.setRespBody(body);
                header.setResultCode("303");
                header.setErrorMsg("调用服务错误!" + var8.getMessage());
                respEntity.setRespHead(header);
            }

            return respEntity;
        }
    }

    private static Boolean ValidHeader(NJReqMsgEntity reqEntity, NJRespMsgEntity respEntity) {
        NJRespMsgHead header = new NJRespMsgHead();
        NJRespMsgBody body = new NJRespMsgBody();
        respEntity.setRespBody(body);
        String temp = null;
        if (reqEntity.getNJReqHeaderEntity() == null) {
            header.setResultCode("101");
            header.setErrorMsg("请求消息头为空");
            respEntity.setRespHead(header);
            return false;
        } else if (reqEntity.getNJReqBodyEntity() == null) {
            header.setResultCode("102");
            header.setErrorMsg("请求消息体为空");
            respEntity.setRespHead(header);
            return false;
        } else {
            temp = reqEntity.getNJReqHeaderEntity().getServiceID();
            if (temp != null && !"".equals(temp)) {
                temp = reqEntity.getNJReqHeaderEntity().getOperationType();
                if (temp != null && !"".equals(temp)) {
                    return true;
                } else {
                    header.setResultCode("302");
                    header.setErrorMsg("未定义操作类型");
                    respEntity.setRespHead(header);
                    return false;
                }
            } else {
                header.setResultCode("301");
                header.setErrorMsg("未定义服务ID");
                respEntity.setRespHead(header);
                return false;
            }
        }
    }

    private static String GetUrl(String serviceID) {
        List<DirectService> directService = cacheSoaService.getDirectServices();

        for(int i = 0; i < directService.size(); ++i) {
            if (((DirectService)directService.get(i)).getName().equals(serviceID)) {
                return ((DirectService)directService.get(i)).getAddress();
            }
        }

        return null;
    }

    private static String RESTInvoke(String url, String strPara) {
        String result = null;

        try {
            URL targetUrl = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection)targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "text/xml");
            strPara = NJEncryptHelper.EncryptToBase64(strPara);
            String input = "<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">" + strPara + "</string>";
            httpConnection.setRequestProperty("Content-Length", String.valueOf(input.length()));
            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code" + httpConnection.getResponseCode());
            }

//            String output;
//            for(BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
//                (output = responseBuffer.readLine()) != null;
//                result = result + output) {
//            }
            // 获取返回的数据
            String strMessage = "";
            StringBuffer buffer = new StringBuffer();
            InputStream inputStream = httpConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((strMessage = bufferedReader.readLine()) != null) {
                buffer.append(strMessage);
            }
//            System.out.println("接收返回值:" + buffer);
            result = buffer.toString();

            httpConnection.disconnect();
        } catch (MalformedURLException var9) {
            var9.printStackTrace();
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        result = result.substring(result.indexOf(62) + 1);
        result = result.substring(0, result.indexOf(60));
        result = NJEncryptHelper.DeEncryptFromBase64(result);
        return result;
    }
}
