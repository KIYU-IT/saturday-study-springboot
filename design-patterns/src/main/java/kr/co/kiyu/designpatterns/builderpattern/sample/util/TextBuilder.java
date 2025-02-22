package kr.co.kiyu.designpatterns.builderpattern.sample.util;

import org.springframework.stereotype.Component;

@Component
public class TextBuilder extends Builder {

    private StringBuffer buffer = new StringBuffer();        // 이 필드에 문서를 구축한다

    public void buildTitle(String title) {                    // 일반 텍스트에서의 타이틀
        buffer.append("==============================\n");    // 장식선
        buffer.append("『" + title + "』\n");                // 『』붙은 타이틀
        buffer.append("\n");                             // 빈행
    }

    public void buildString(String str) {                     // 일반 텍스트에서의 문자열
        buffer.append('■' + str + "\n");                   // ■붙은 문자열
        buffer.append("\n");                              // 빈행
    }

    public void buildItems(String[] items) {                  // 일반 텍스트에서의 개별항목
        for (int i = 0; i < items.length; i++) {
            buffer.append(" ㆍ" + items[i] + "\n");           // ㆍ붙은 항목
        }

        buffer.append("\n");                               // 빈행
    }

    public void buildDone() {                                // 문서의 완성
        buffer.append("==============================\n");     // 장식선
    }

    public String getResult() {
        return buffer.toString();                              // StringBuffer을 Stringd로 변환
    }

}
