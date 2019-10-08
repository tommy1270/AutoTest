package model;

import lombok.Data;

@Data
public class AddDepartmentCase {
    private int id;
    private int level;
    private String name;
    private int parentId;
    private int expected;
}
