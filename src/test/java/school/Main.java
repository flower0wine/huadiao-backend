package school;

import java.util.*;
public class Main {
    private enum Choice {
        ADD(1), DELETE(2), MODIFY(3), QUERY(4), EXIT(5);
        public Integer choice;
        Choice(Integer choice) {
            this.choice = choice;
        }
    }
    private static class Student {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private interface Operation {
        void operate();
    }

    private static class AddStudent implements Operation {
        @Override
        public void operate() {
            System.out.println("输入学生名称");
            String name = scanner.next();
            Student student = new Student();
            student.setName(name);
            students.add(student);
        }
    }

    private static class ModifyStudent implements Operation {
        @Override
        public void operate() {
            System.out.println("请输入旧生在数组中的下标");
            int index = scanner.nextInt();
            Student student = students.get(index);
            System.out.println("请输入新名称");
            String name = scanner.next();
            student.setName(name);
        }
    }

    private static class DeleteStudent implements Operation {
        @Override
        public void operate() {
            System.out.println("请输入要删除的学生在数组中的下标");
            int index = scanner.nextInt();
            students.remove(index);
        }
    }

    private static class QueryStudent implements Operation {
        @Override
        public void operate() {
            System.out.println("查询信息如下: ");
            System.out.println("===================");
            students.forEach(System.out::println);
            System.out.println("===================");
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Map<Choice, Operation> map = new HashMap<>(){{
            put(Choice.ADD, new AddStudent());
            put(Choice.MODIFY, new ModifyStudent());
            put(Choice.DELETE, new DeleteStudent());
            put(Choice.QUERY, new QueryStudent());
        }};
        while(true) {
            System.out.println("1 - 添加学生信息");
            System.out.println("2 - 删除学生信息");
            System.out.println("3 - 修改学生信息");
            System.out.println("4 - 查询学生信息");
            System.out.println("5 - 退出");

            int choice = scanner.nextInt();
            for (Choice c : Choice.values()) {
                if(Choice.EXIT.choice.equals(choice)) {
                    return;
                }
                else if(c.choice.equals(choice)) {
                    map.get(c).operate();
                    break;
                }
            }
        }
    }
}
