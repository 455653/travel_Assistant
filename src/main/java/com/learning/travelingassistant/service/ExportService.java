
package com.learning.travelingassistant.service;

import org.apache.poi.xwpf.usermodel.*;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExportService {

    private static final Logger logger = LoggerFactory.getLogger(ExportService.class);

    public byte[] exportGuideToWord(String title, String markdownContent) throws IOException {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            addTitle(document, title);

            Parser parser = Parser.builder().build();
            Node markdownNode = parser.parse(markdownContent);

            processNode(document, markdownNode);

            document.write(out);
            logger.info("成功生成 Word 文档，标题: {}, 大小: {} 字节", title, out.size());
            return out.toByteArray();

        } catch (Exception e) {
            logger.error("导出 Word 文档失败: {}", e.getMessage(), e);
            throw new IOException("导出 Word 文档失败", e);
        }
    }

    private void addTitle(XWPFDocument document, String title) {
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText(title);
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.setFontFamily("微软雅黑");

        titleParagraph.setSpacingAfter(300);
    }

    private void processNode(XWPFDocument document, Node node) {
        Node current = node.getFirstChild();
        while (current != null) {
            if (current instanceof Heading) {
                processHeading(document, (Heading) current);
            } else if (current instanceof Paragraph) {
                processParagraph(document, (Paragraph) current);
            } else if (current instanceof BulletList) {
                processList(document, (BulletList) current, false);
            } else if (current instanceof OrderedList) {
                processList(document, (OrderedList) current, true);
            } else if (current instanceof ThematicBreak) {
                addHorizontalLine(document);
            }
            current = current.getNext();
        }
    }

    private void processHeading(XWPFDocument document, Heading heading) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        String text = extractText(heading);
        run.setText(text);
        run.setBold(true);
        run.setFontFamily("微软雅黑");

        int level = heading.getLevel();
        if (level == 1) {
            run.setFontSize(18);
            paragraph.setSpacingBefore(200);
            paragraph.setSpacingAfter(150);
        } else if (level == 2) {
            run.setFontSize(16);
            paragraph.setSpacingBefore(150);
            paragraph.setSpacingAfter(100);
        } else {
            run.setFontSize(14);
            paragraph.setSpacingBefore(100);
            paragraph.setSpacingAfter(80);
        }
    }

    private void processParagraph(XWPFDocument document, Paragraph para) {
        String text = extractText(para);
        if (text != null && !text.trim().isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(text);
            run.setFontFamily("微软雅黑");
            run.setFontSize(11);
            paragraph.setSpacingAfter(100);
        }
    }

    private void processList(XWPFDocument document, ListBlock listBlock, boolean ordered) {
        int index = 1;
        Node item = listBlock.getFirstChild();
        while (item != null) {
            if (item instanceof ListItem) {
                XWPFParagraph paragraph = document.createParagraph();
                paragraph.setIndentationLeft(400);

                XWPFRun run = paragraph.createRun();
                String prefix = ordered ? (index + ". ") : "• ";
                String text = extractText(item);
                run.setText(prefix + text);
                run.setFontFamily("微软雅黑");
                run.setFontSize(11);
                paragraph.setSpacingAfter(80);
                index++;
            }
            item = item.getNext();
        }
    }

    private void addHorizontalLine(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingAfter(100);
        paragraph.setSpacingBefore(100);
    }

    private String extractText(Node node) {
        StringBuilder text = new StringBuilder();
        extractTextRecursive(node, text);
        return text.toString().trim();
    }

    private void extractTextRecursive(Node node, StringBuilder text) {
        if (node instanceof Text) {
            text.append(((Text) node).getLiteral());
        } else {
            Node child = node.getFirstChild();
            while (child != null) {
                extractTextRecursive(child, text);
                child = child.getNext();
            }
        }
    }
}