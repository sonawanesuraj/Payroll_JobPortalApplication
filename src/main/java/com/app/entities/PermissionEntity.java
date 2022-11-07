package com.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "permissions")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE permissions SET is_active=false WHERE id=?")
public class PermissionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "action_name")
	private String actionName;

	@Column(name = "description")
	private String description;

	@Column(name = "method")
	private String method;

	@Column(name = "baseUrl")
	private String baseUrl;

	@Column(name = "path")
	private String path;

	@Column(name = "is_active")
	private boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permission", cascade = CascadeType.ALL)
	@JsonBackReference
	List<RolePermissionEntity> rolePermission;

	public PermissionEntity() {
		super();
	}

	public PermissionEntity(Long id, String actionName, String description, String method, String baseUrl, String path,
			boolean isActive, Date createdAt, Date updatedAt, List<RolePermissionEntity> rolePermission) {
		super();
		this.id = id;
		this.actionName = actionName;
		this.description = description;
		this.method = method;
		this.baseUrl = baseUrl;
		this.path = path;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.rolePermission = rolePermission;
	}

	public List<RolePermissionEntity> getRolePermission() {
		return rolePermission;
	}

	public void setRolePermission(List<RolePermissionEntity> rolePermission) {
		this.rolePermission = rolePermission;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
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
		this.updatedAt = updatedAt;
	}

}
