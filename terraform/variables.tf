# precisa começar com TF_VAR_
variable "TF_VAR_POSTGRES_USER" {
  description = "The master username for the database."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_POSTGRES_PASSWORD" {
  description = "The master password for the database."
  type        = string
  sensitive   = true
}

# precisa começar com TF_VAR_
variable "TF_VAR_IMAGE_VERSION" {
  description = "The number of the new image version."
  type        = string
}