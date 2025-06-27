import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberManager {
    List<Member> members = new ArrayList<>();
    //회원추가
    public void addMember(Member member){
        members.add(member);
    }
    //회원 전체 조회
    public void printAllMembers()
    {
//        List<Member> members=this.members;
        members.forEach(member -> System.out.println(member));
    }
    //이름으로 회원 검색
//    public Member findByName(String name){
//        List<Member> members=this.members;
//        Member member=members.stream()
//                .filter(m->m.getName().equals(name))
//                .findFirst()
//                .orElse(null);
//    }
    public Optional<Member> findByName(String name){
//        List<Member> members=this.members;
//        Member member=members.stream()
//                .filter(m->m.getName().equals(name))
//                .findFirst()
//                .orElse(null);
        return members.stream()
                .filter(m -> m.getName().equals(name))
                .findFirst();
    }
    //ID로 회원 삭제
    public void removeById(int id){
        members.removeIf(member -> member.getId() == id);
    }
    public static void main(String[] args) {
        MemberManager manager = new MemberManager();

        manager.addMember(new Member(1, "예린", 23));
        manager.addMember(new Member(2, "세현", 25));
        manager.addMember(new Member(3, "다은", 24));

        manager.printAllMembers();

//        Member found = manager.findByName("세현");
        Optional<Member> found=manager.findByName("세현");
        found.ifPresent(member -> System.out.println("찾은 회원: "+member.getName()));
//        System.out.println("찾은 회원: " + found);

        manager.removeById(2);
        manager.printAllMembers();
    }
}
