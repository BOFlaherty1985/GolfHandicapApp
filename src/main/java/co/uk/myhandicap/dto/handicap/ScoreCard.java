package main.java.co.uk.myhandicap.dto.handicap;

import main.java.co.uk.myhandicap.dto.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User Score Card Object
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 08/07/2014
 * @project MyHandicapApp
 */
@Entity
@Table(name="SCORE_CARDS")
public class ScoreCard {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name="PLAYER")
    private Long playerId;
    @Column(name="SUBMITTED_DATE")
    private Date submittedDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name="SCORE_CARD_ID"), inverseJoinColumns = @JoinColumn(name="ROUND_ID"))
    private List<Round> golfRounds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public List<Round> getGolfRounds() {
        return golfRounds;
    }

    public void setGolfRounds(List<Round> golfRounds) {
        this.golfRounds = golfRounds;
    }

    @Override
    public String toString() {
        return "ScoreCard{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", submittedDate=" + submittedDate +
                ", golfRounds=" + golfRounds +
                '}';
    }
}
