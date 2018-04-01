package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
@RestController
@RequestMapping(value = "/wordLadder")
public class Controller {

    public static Stack findLadder(String word_1,String word_2,Set dict){
        Stack<String> s1 = new Stack<String>();
        Queue<Stack<String>> ladder = new LinkedList<Stack<String>>();
        Set history = new HashSet();
        s1.push(word_1);
        ladder.offer(s1);
        int len = word_1.length();
        Set w2_prev = new HashSet();
        for (int index = 0; index < word_2.length(); index++) {
            String tp = word_2;
            for (char alpha = 'a'; alpha <= 'z'; alpha++) {
                StringBuilder strb = new StringBuilder(tp);
                String s = String.valueOf(alpha);
                strb.replace(index, index+1,s);
                String nstr = new String();
                nstr = strb.toString();
                if (dict.contains(nstr) && nstr!=word_2) {
                    w2_prev.add(nstr);
                }
            }
        }
        if(word_1.length() == word_2.length()) {
            while (!ladder.isEmpty()) {
                Stack<String> ss = new Stack<String>();
                ss = ladder.poll();
                String word = new String();
                word = ss.peek();
                for (int index = 0; index < len; index++) {
                    String tp = word;
                    for (char alpha = 'a'; alpha <= 'z'; alpha++) {
                        StringBuilder strb = new StringBuilder(tp);
                        String s = String.valueOf(alpha);
                        strb.replace(index, index + 1, s);
                        tp = strb.toString();
                        if (!(history.contains(tp)) && (dict.contains(tp))) {
                            if (w2_prev.contains(tp)) {
                                ss.add(tp);
                                ss.add(word_2);
                                return ss;
                            } else {
                                Stack<String> s2 = (Stack<String>) ss.clone();
                                s2.push(tp);
                                history.add(tp);
                                ladder.add(s2);
                            }
                        }
                    }
                }
            }
        }
        else{
            while (!ladder.isEmpty()) {
                Stack<String> ss = new Stack<String>();
                ss = ladder.poll();
                String word = new String();
                word = ss.peek();
                for (int index = 0; index < word.length(); index++) {
                    String tp = word;
                    // equal lengths querying
                    for (char alpha = 'a'; alpha <= 'z'; alpha++) {
                        StringBuilder strb = new StringBuilder(tp);
                        String s = String.valueOf(alpha);
                        strb.replace(index, index + 1, s);
                        tp = strb.toString();
                        if (!(history.contains(tp)) && (dict.contains(tp))) {
                            if (w2_prev.contains(tp)) {
                                ss.add(tp);
                                ss.add(word_2);
                                return ss;
                            } else {
                                Stack<String> s2 = (Stack<String>) ss.clone();
                                s2.push(tp);
                                history.add(tp);
                                ladder.add(s2);
                            }
                        }
                    }
                    // add lengths querying
                    for (char alpha = 'a'; alpha <= 'z'; alpha++) {
                        tp = word;
                        StringBuilder strb = new StringBuilder(tp);
                        String s = String.valueOf(alpha);
                        strb.insert(index, s);
                        tp = strb.toString();

                        if (!(history.contains(tp)) && (dict.contains(tp))) {
                            if (w2_prev.contains(tp)) {
                                ss.add(tp);
                                ss.add(word_2);
                                return ss;
                            } else {
                                Stack<String> s2 = (Stack<String>) ss.clone();
                                s2.push(tp);
                                history.add(tp);
                                ladder.add(s2);
                            }
                        }
                    }
                    // decrease lengths querying

                    for (char alpha = 'a'; alpha <= 'z'; alpha++) {
                        tp = word;
                        StringBuilder strb = new StringBuilder(tp);
                        String s = String.valueOf(alpha);
                        strb.delete(index,index+1);
                        tp = strb.toString();
                        if (!(history.contains(tp)) && (dict.contains(tp))) {
                            if (w2_prev.contains(tp)) {
                                ss.add(tp);
                                ss.add(word_2);
                                return ss;
                            } else {
                                Stack<String> s2 = (Stack<String>) ss.clone();
                                s2.push(tp);
                                history.add(tp);
                                ladder.add(s2);
                            }
                        }
                    }
                }
            }

        }
        Stack solution = new Stack();
        return solution;
    }
    public static int forTest(int input){
        return input;
    }
    @RequestMapping(value = "/word_1={word_1}&word_2={word_2}",method = RequestMethod.GET)
    public static String wordLadder(@PathVariable String word_1,@PathVariable String word_2) {
        File f = new File("src/dictionary.txt");
        Set dict = new HashSet();
        Stack result = new Stack();
        String er = "BAD INPUT!\n";
        try {
            FileInputStream out = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line = null;
            while ((line = br.readLine()) != null) {
                dict.add(line);
            }
            dict.add(" ");

                if(word_1.length() == 0){
                    return er;
                }
                boolean isWord = word_1.matches("[a-zA-Z]+");
                if(!isWord){
                    return er;
                }
                if(word_2.length() == 0){
                    return er;
                }
                isWord = word_2.matches("[a-zA-Z]+");
                if(!isWord){
                    return er;
                }
                word_1 = word_1.toLowerCase();
                word_2 = word_2.toLowerCase();
                if (word_1 == word_2) {
                    String ss = "The two words must be different.\n";
                    return ss;
                }
                if(word_1.length() == word_2.length() || word_1.length() != word_2.length()){
                    result = findLadder(word_1, word_2, dict);
                    if(result.isEmpty()){
                        String no = "No word ladder found!\n";

                    }
                    else {
                        StringBuilder strb = new StringBuilder();
                        while(!result.isEmpty()) {
                            strb.append(result.pop());
                            strb.append(" ");
                        }
                        String ss = strb.toString();
                        return ss;
                    }
                }
                else{
                    return er;
                }
            }

        catch(Exception e){
            return er;
        }
        return er;
    }


}
