package edu.sjsu.cmpe275.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.sjsu.cmpe275.email.ActivationEmail;
import edu.sjsu.cmpe275.model.Application;
import edu.sjsu.cmpe275.model.CompanyJobPosts;
import edu.sjsu.cmpe275.model.JobSeeker;
import edu.sjsu.cmpe275.model.User;
import edu.sjsu.cmpe275.service.ApplicationService;
import edu.sjsu.cmpe275.service.CompanyJobsService;
import edu.sjsu.cmpe275.service.JobseekerService;
import edu.sjsu.cmpe275.service.UserService;

@Controller
public class ApplicationController {
    
    @Autowired
    private CompanyJobsService companyJobsService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private JobseekerService jobseekerService;
    
    @Autowired
    private ApplicationService applicationService;
    
    private static String RESUME_FOLDER = "src/main/webapp/resumes/";

    @RequestMapping(value = "/applyjob/{jobid}", method = RequestMethod.GET)
    public String applyJob(@PathVariable("jobid") Long jobid, Model model,HttpSession session) {
       
    	
		CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
        model.addAttribute("companyjobposts", jobPost);
       	session.setAttribute("jobid", jobid);
        return "applyjob";
    }


    @RequestMapping(value = "/applyprofile", method = RequestMethod.GET)
    public String profileget(Model model) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
        model.addAttribute("jobseeker", js);
        return "applyprofile";
    }
    
    
    @PostMapping("/applyprofile")
    public String profilepost(Model model, HttpSession session) {
    	String resume = null;
    	String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
    	String id = user.getId()+"+"+session.getAttribute("jobid");
        String name = js.getFirstname()+ " "+ js.getLastname();
        long jobid=  (long) session.getAttribute("jobid");
        Application application = new Application(id,user.getId(),jobid,name,user.getEmailid(),resume,"Pending" );
        applicationService.save(application);
        
        CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
        ActivationEmail.emailAppliedJob(user.getEmailid(), jobid,jobPost.getCompany().getName(),jobPost.getTitle(),jobPost.getDescrip(),jobPost.getLoc());
     
        return "redirect:welcome";
    }
    
    
    @PostMapping("/applyresume")
    public String resumeUpload(@RequestParam("file") MultipartFile file,HttpSession session) {

        if (file.isEmpty()) {
            System.out.println("its empty");
            return "applyresume";
        }
        String id=null;
        Path path=null;
        String fileLoc =null;
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUserName);
        JobSeeker js=jobseekerService.findById(user.getId());
        try {

            byte[] bytes = file.getBytes();
            id = user.getId()+"+"+session.getAttribute("jobid");
            String fileName = file.getOriginalFilename();
            int lastIndex = fileName.lastIndexOf('.');
            String substring = fileName.substring(lastIndex, fileName.length());
            fileLoc= RESUME_FOLDER + id+substring;
            path = Paths.get(fileLoc);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = js.getFirstname()+ " "+ js.getLastname();
        long jobid=  (long) session.getAttribute("jobid");
        Application application = new Application(id,user.getId(),jobid,name,user.getEmailid(),fileLoc,"Pending" );
        applicationService.save(application);
        
        CompanyJobPosts jobPost = companyJobsService.findByJobId(jobid);
        ActivationEmail.emailAppliedJob(user.getEmailid(), jobid,jobPost.getCompany().getName(),jobPost.getTitle(),jobPost.getDescrip(),jobPost.getLoc());
        
        return "redirect:welcome";
    }
    
    
    @GetMapping("/applyresume")
    public String resumeget() {
  
        return "applyresume";
    }
}
