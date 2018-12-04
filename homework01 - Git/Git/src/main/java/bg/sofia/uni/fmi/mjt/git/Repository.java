package bg.sofia.uni.fmi.mjt.git;

import java.util.*;

public class Repository {
    private ArrayList<String> files;
    private ArrayList<String> currentFiles;
    private int changedFiles;

    private String branch;
    private String commitHash;
    private Map<String, ArrayList<Commit>> repository;

    public Map<String, ArrayList<Commit>> getRepository() {
        return repository;
    }

    public Repository() {
        this.files = new ArrayList<>();
        this.changedFiles = 0;
        this.branch = "master";
        this.repository = new LinkedHashMap<>();
        this.repository.put(branch, new ArrayList<>());
        this.currentFiles = new ArrayList<>();
    }

    public String getBranch() {
        return branch;
    }

    public Result createBranch(String name) {
        Result result = new Result();
        if (repository.containsKey(name)) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CREATE_BRANCH, name));
            result.setSuccessful(false);
        } else {
            repository.put(name, getCurrentBranchCommits());
            result.setMessage(String.format(Constants.SUCCESSFUL_CREATE_BRANCH, name));
            result.setSuccessful(true);
        }
        return result;
    }

    public Result checkoutBranch(String name) {
        Result result = new Result();
        if (repository.containsKey(name)) {
            this.branch = name;
            this.currentFiles = new ArrayList<>();
            result.setMessage(String.format(Constants.SUCCESSFUL_CHECKOUT_BRANCH, name));
            result.setSuccessful(true);
        } else {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CHECKOUT_BRANCH, name));
            result.setSuccessful(false);
        }

        return result;
    }

    public Result checkoutCommit(String hash) {
        Result result = new Result();

        Commit commit = getCurrentBranchCommits()
                .stream()
                .filter(t -> t.getHash().equals(hash))
                .findFirst()
                .orElse(null);

        if (commit == null) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_CHECKOUT_COMMIT, hash));
            result.setSuccessful(false);
        } else {
            this.commitHash = hash;
            files = commit.getFiles();
            result.setMessage(String.format(Constants.SUCCESSFUL_CHECKOUT_COMMIT, hash));
            result.setSuccessful(true);
        }
        return result;
    }

    public Result add(String... files) {
        if (files.length == 0)
            return null;
        Result result = new Result();
        StringBuilder sb = new StringBuilder();

        for (String file :
                files) {
            sb.append(file).append(", ");

            if (this.files.indexOf(file) != -1) {
                sb.delete(0, sb.length());
                sb.append(file);

                result.setMessage(String.format(Constants.UNSUCCESSFUL_ADD, sb.toString()));
                result.setSuccessful(false);

                return result;
            }
        }

        result.setSuccessful(true);
        sb.delete(sb.length() - 2, sb.length());
        result.setMessage(String.format(Constants.SUCCESSFUL_ADD, sb.toString()));
        this.files.addAll(Arrays.asList(files));
        this.changedFiles += files.length;
        this.currentFiles.addAll(Arrays.asList(files));

        return result;
    }

    public Result remove(String... files) {
        Result result = new Result();
        Set<Integer> indexes = new HashSet<>();
        for (String file :
                files) {
            int index = this.files.indexOf(file);
            if (index != -1) {
                indexes.add(index);
            } else {
                result.setMessage(String.format(Constants.UNSUCCESSFUL_REMOVE, file));
                result.setSuccessful(false);
                return result;
            }
        }

        StringBuilder sb = new StringBuilder();
        int indexRemoval = 0;
        for (int index :
                indexes) {
            index -= indexRemoval++;
            sb.append(this.files.get(index)).append(", ");
            for (String file :
                    files) {
                if (currentFiles.contains(file))
                    this.changedFiles -= 2;
            }
            this.files.remove(index);
        }
        sb.delete(sb.length() - 2, sb.length());
        result.setMessage(String.format(Constants.SUCCESSFUL_REMOVE, sb.toString()));
        result.setSuccessful(true);
        this.changedFiles += files.length;

        return result;
    }

    public Result commit(String message) {
        Result result = new Result();

        if (changedFiles > 0) {
            Commit commit = new Commit(message, new ArrayList<>(files));
            this.commitHash = commit.getHash();
            ArrayList<Commit> commits = getCurrentBranchCommits();
            commits.add(commit);
            result.setSuccessful(true);
            result.setMessage(String.format(Constants.SUCCESSFUL_COMMIT, changedFiles));
            this.repository.put(branch, commits);
            this.changedFiles = 0;
            this.currentFiles = new ArrayList<>();
        } else {
            result.setMessage(Constants.UNSUCCESSFUL_COMMIT);
            result.setSuccessful(false);
        }
        return result;
    }

    public Result log() {
        Result result = new Result();

        if (getCurrentBranchCommits().size() == 0) {
            result.setMessage(String.format(Constants.UNSUCCESSFUL_LOG, branch));
            result.setSuccessful(false);
        } else {
            StringBuilder sb = new StringBuilder();
            ArrayList<Commit> currentBranchCommits = getCurrentBranchCommits();
            for (int i = currentBranchCommits.size() - 1; i >= 0; i--) {
                Commit commit = currentBranchCommits.get(i);
                sb.append(String.format(Constants.SUCCESSFUL_LOG, commit.getHash(), commit.getDate(), commit.getMessage()));
                if (i != 0) {
                    sb.append("\n\n");
                }
                result.setSuccessful(true);
            }
            result.setMessage(sb.toString());
        }

        return result;

    }

    public Commit getHead() {
        int size = getCurrentBranchCommits().size() - 1;
        return size == -1 ? null : repository.get(branch).get(size);
    }

    private ArrayList<Commit> getCurrentBranchCommits() {
        ArrayList<Commit> commits = new ArrayList<>();
        for (Commit commit :
                repository.get(branch)) {
            commits.add(commit);
            if (commit.getHash().equals(commitHash))
                break;
        }
        return commits;
    }
}