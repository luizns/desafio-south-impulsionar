package com.br.luizns.produtoapi.util;

import com.br.luizns.produtoapi.entity.Produto;
import com.br.luizns.produtoapi.service.ProdutoService;
import com.br.luizns.produtoapi.service.exceptions.ResourceNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProdutoUtil {

    public static String TYPE = "text/csv";
    static String[] HEADERS = {"código", "codigo de barras", "série", "nome", "descrição", "categoria", "valor bruto", "impostos (%)", "data de fabricação", "data de validade", "cor", "material"};

    public static boolean temFormatoCSV(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    public static List<Produto> csvParaProduto(InputStream is) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Produto> produtos = new ArrayList<Produto>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String codigoProduto = csvRecord.get(HEADERS[0]);
                String codigoDeBarras = csvRecord.get(HEADERS[1]);
                String serie = csvRecord.get(HEADERS[2]);
                String nome = csvRecord.get(HEADERS[3]);
                String descricao = csvRecord.get(HEADERS[4]);
                String categoria = csvRecord.get(HEADERS[5]);
                BigDecimal valorBruto = toBigDecimal(csvRecord.get(HEADERS[6]));
                BigDecimal impostos = toBigDecimal(csvRecord.get(HEADERS[7]));
                LocalDate dataDeFabricacao = localDatFormat(csvRecord.get(HEADERS[8]));
                LocalDate dataDeValidade = localDatFormat(csvRecord.get(HEADERS[9]));
                String cor = csvRecord.get(HEADERS[10]);
                String material = csvRecord.get(HEADERS[11]);
                Integer quantidade = 0;
                BigDecimal valorFinal = null;


                Produto produto = new Produto(null, codigoProduto, codigoDeBarras, serie, nome, descricao, categoria, valorBruto, impostos, dataDeFabricacao, dataDeValidade, cor, material, quantidade, null);
                produto.setValorFinal(ProdutoService.getValorFinal(produto));
                produtos.add(produto);

            }

            return produtos;
        } catch (IOException e) {
            throw new ResourceNotFoundException("Falha ao analisar o arquivo CSV: " + e.getMessage());
        }
    }

    public static BigDecimal toBigDecimal(String valor) {
        try {
            return new BigDecimal(valor
                    .replace(",", ".")
                    .replace("\"", ""))
                    .setScale(2, RoundingMode.HALF_EVEN);
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }

    public static LocalDate localDatFormat(String date) {

        if (date.equals("n/a") || date.isEmpty()) {
            return null;
        } else {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }
}