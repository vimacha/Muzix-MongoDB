package com.stackroute.musicservice.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
   // @Column(name="trackid")
    private int trackId;
   // @Column(name="trackname")
    private String trackName;
   // @Column(name="trackcomments")
    private String trackComments;
}
