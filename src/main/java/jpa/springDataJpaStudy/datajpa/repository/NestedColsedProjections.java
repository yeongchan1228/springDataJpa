package jpa.springDataJpaStudy.datajpa.repository;

public interface NestedColsedProjections {

    String getUsername();
    TeamInfo getTeam(); // 최적화는 안된다. Root만 최적화 된다.

    interface TeamInfo{
        String getName();
    }
}

