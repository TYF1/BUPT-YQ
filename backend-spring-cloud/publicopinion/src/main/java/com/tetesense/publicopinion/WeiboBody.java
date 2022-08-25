package com.tetesense.publicopinion;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="weibo")
public class WeiboBody {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "text")
    private String text;

    @Column(name = "pics")
    private String picUrl;

    @Column(name = "video_url")
    private String vidUrl;

    @Column(name = "topics")
    private String topic;

    @Column(name = "attitudes_count")
    private Integer likes;

    @Column(name = "comments_count")
    private Integer comments;

    @Column(name = "reposts_count")
    private Integer reposts;

    @Column(name = "screen_name")
    private String username;

    @Column(name = "created_at")
    private Date createdAt;
}
