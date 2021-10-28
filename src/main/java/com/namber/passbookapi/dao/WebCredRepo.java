package com.namber.passbookapi.dao;

import com.namber.passbookapi.model.WebCredential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebCredRepo extends CrudRepository<WebCredential, String> {
    public List<WebCredential> findByUsername(String username);

    @Query(value="SELECT cred_group_name from web_credential where username=?1", nativeQuery = true)
    public List<String> getGroupNames(String username);

    @Query(value="SELECT * FROM web_credential WHERE username=?1 and cred_group_name=?2 and parent_cred_name=?3", nativeQuery = true)
    public List<WebCredential> getCredByParentName(String user, String groupName, String credParentName);

    @Query(value="SELECT * FROM web_credential WHERE username=?1 and cred_group_name=?2 and cred_name=?3", nativeQuery = true)
    List<WebCredential> getCredByName(String user, String groupName, String credName);
}
