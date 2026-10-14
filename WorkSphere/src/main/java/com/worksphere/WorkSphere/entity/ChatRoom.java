package com.worksphere.WorkSphere.entity;

import com.worksphere.WorkSphere.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseEntity {

    private String name;

    private Boolean isGroup = false;
}