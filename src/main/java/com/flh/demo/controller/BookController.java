package com.flh.demo.controller;

import com.flh.demo.bean.Book;
import com.flh.demo.bean.BookCart;
import com.flh.demo.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fulinhua on 2016/12/10.
 */
@Controller
public class BookController {

    @Autowired
    private BookDao bookMapper;


    @RequestMapping(value="/book_input")
    public String inputBook(Model model){
       // model.addAttribute("book",new Book());
        return "BookAddForm";
    }
    @RequestMapping(value="/book_save")
    public String saveBook(Book book){
        bookMapper.add(book);
        return "redirect:/book_list";
    }
    @RequestMapping(value="/book_list")
    public String showBook(Model model) throws SQLException {
        List<Book> list=bookMapper.getList();
        model.addAttribute("books",list);
        return "BookShow";
    }
    @RequestMapping(value="/book_remove/{id}")
    public String removeBook(@PathVariable int id, Model model) throws SQLException {
        bookMapper.delete(id+"");
        List<Book> list=bookMapper.getList();
        model.addAttribute("books",list);
        return "redirect:/book_list";
    }

    @RequestMapping(value="/book_edit/{id}")//跳转到编辑页面
    public String editBook(Model model, @PathVariable int id){
         Book book=bookMapper.get(id+"");
        System.out.println(id);
        model.addAttribute("book",book);
        return "BookEditForm";
    }

    @RequestMapping(value="/book_buy/{id}")
    public String BuyBook(Model model, @PathVariable int id, HttpServletRequest request){
        String sessionID=request.getSession().getId();
        bookMapper.addCart(sessionID,id,1);
        Book book=bookMapper.get(id+"");
       List<BookCart> booklist=bookMapper.getCart(sessionID);
        model.addAttribute("booklist",booklist);
        return "BookCart";
    }
    @RequestMapping(value="/book_showbuy")
    public String showBookBuy(Model model) throws SQLException {
        List<Book> list=bookMapper.getList();
        model.addAttribute("books",list);
        return "BookBuy";
    }

    @RequestMapping(value="/book_edit/book_update")
    public String updateBook( Book book){
        bookMapper.update(book);
        return "redirect:/book_list";
    }
    @RequestMapping(value="/show_cart")
    public String showcart(HttpServletRequest request,Model model){
        String sessionID=request.getSession().getId();
        List<BookCart> booklist=new ArrayList<BookCart>();
        booklist=bookMapper.getCart(sessionID);
        model.addAttribute("booklist",booklist);
        return "BookCart";
    }

    @RequestMapping(value="{*}/book_removecart/{id}")
    public String removeBookFromCart(HttpServletRequest request,Model model,@PathVariable int id){
        String sessionID=request.getSession().getId();
        bookMapper.removeBookFromCart(sessionID,id);
        List<BookCart> booklist=new ArrayList<BookCart>();
        booklist=bookMapper.getCart(sessionID);
        model.addAttribute("booklist",booklist);
        return "BookCart";
    }

}
