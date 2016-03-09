/******************************************************************************
 * * This data and information is proprietary to, and a valuable trade secret
 * * of, Basis Technology Corp.  It is given in confidence by Basis Technology
 * * and may only be used as permitted under the license agreement under which
 * * it has been distributed, and in no other way.
 * *
 * * Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 * *
 * * The technical data and information provided herein are provided with
 * * `limited rights', and the computer software provided herein is provided
 * * with `restricted rights' as those terms are defined in DAR and ASPR
 * * 7-104.9(a).
 ******************************************************************************/

package com.basistech.mext;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
@Component(role = AbstractMavenLifecycleParticipant.class, hint = "com.basistech.version")
public class VersionGeneratorLifecycleParticipant extends AbstractMavenLifecycleParticipant {
    private static final Logger LOG = LoggerFactory.getLogger(VersionGeneratorLifecycleParticipant.class);

    @Override
    public void afterSessionStart(MavenSession session) throws MavenExecutionException {
        LOG.info("begin");
        File projectBase = session.getCurrentProject().getBasedir();
        File policyFile = new File(projectBase, "version-policy.txt");
        if (policyFile.exists()) {
            String template;
            try {
                template = fileContents(policyFile);
            } catch (IOException e) {
                throw new MavenExecutionException("Failed to read " + policyFile.getAbsolutePath(), e);
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = format.format(new Date());
            String rev = template.replace("${timestamp}", timestamp);
            session.getUserProperties().put("rev", rev);
        }
    }

    private String fileContents(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("utf-8")))) {
            return reader.readLine(); // we expect One Line.
        }
    }
}
