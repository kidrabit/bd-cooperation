package kr.co.pcninc.bigdata.cooperationmodule.domain;


import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageRequest {
    private int page;
    private int size;
    private Sort.Direction direction;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page <= 0? 1 : page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

   /* public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction == null ? Direction.DESC : direction;
    }*/

    public org.springframework.data.domain.PageRequest of(){
        return org.springframework.data.domain.PageRequest.of(page - 1, size);
    }
}
