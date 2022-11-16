package com.app.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "role_permission")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE role_permission SET is_active=false WHERE id=?")
public class RolePermissionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private RoleEntity role;

	@ManyToOne(fetch = FetchType.LAZY)
	private PermissionEntity permission;

	private Boolean isActive = true;

	@CreationTimestamp
	private Date createdAt;

	@UpdateTimestamp
	private Date updatedAt;

	public RolePermissionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RolePermissionEntity(Long id, RoleEntity role, PermissionEntity permission, Boolean isActive, Date createdAt,
			Date updatedAt) {
		super();
		this.id = id;
		this.role = role;
		this.permission = permission;
		this.isActive = isActive;
		this.createdAt = createdAt;
		updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public PermissionEntity getPermission() {
		return permission;
	}

	public void setPermission(PermissionEntity permission) {
		this.permission = permission;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		updatedAt = updatedAt;
	}

}
